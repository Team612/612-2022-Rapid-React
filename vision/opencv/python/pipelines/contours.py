import cv2 as cv
import numpy as np
from pipelines.base import Base

class Contour:
    def __init__(self, mat):
        self.mat = mat

    def getArea(self):
        if not hasattr(self, '_area'):
            self._area = cv.contourArea(self.mat)
        return self._area
    
    def getMoments(self):
        if not hasattr(self, '_moments'):
            self._moments = cv.moments(self.mat)
        return self._moments

class FindContoursResult:
    def __init__(self, settings, img, cvContours, hierarchy = None):
        self.settings = settings
        self.img = img
        self.cvContours = cvContours
        self.hierarchy = hierarchy
        self.contours = [Contour(c) for c in cvContours]
    
    def getArea(self):
        result = 0.0
        
        for i in range(len(self.contours)):
            result += self.contours[i].getArea()
            
        return result
    
    def getAverageArea(self):
        return (self.getArea() / len(self.contours)) if len(self.contours) > 0 else 0

class FindContours(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.enabled = settings.get('enabled', True)
            
    def run(self, img):
        if self.enabled:
            res = cv.findContours(img, cv.RETR_EXTERNAL, cv.CHAIN_APPROX_TC89_KCOS)
            contours = res[0]
            hierarchy = res[1]
            return FindContoursResult(self.settings, img, contours, hierarchy)
        else:
            return img

    def dump(self):
        return { 'type': 'FindContours' }

class SpeckleRejectResult:
    def __init__(self, settings, input, contours):
        self.settings = settings
        self.input = input
        self.img = input.img
        self.contours = contours
        self.cvContours = [c.mat for c in contours]
        # TODO: filter hierarchy too?
        self.hierarchy = input.hierarchy
        
    def draw(self, dest, color):
        for i in range(len(self.cvContours)):
            cv.drawContours(dest, self.cvContours, i, color, 2, cv.LINE_8, self.hierarchy, 0)

class SpeckleReject(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.minAllowedAreaPercent = settings.get('minAllowedAreaPercent', 0)
        self.enabled = settings.get('enabled', True)

    def run(self, input):
        if self.enabled:
            minAllowedArea = self.minAllowedAreaPercent / 100.0 * input.getAverageArea()
            filteredContours = [c for c in input.contours if c.getArea() >= minAllowedArea]
            return SpeckleRejectResult(self.settings, input, filteredContours)
        else:
            return input
            
    def gui(self, window):
        cv.createTrackbar(
            'Speckle Reject',
            window,
            self.minAllowedAreaPercent,
            100,
            lambda value: self._setMinAllowedAreaPercent(value))

    def _setMinAllowedAreaPercent(self, value):
        self.minAllowedAreaPercent = value

    def dump(self):
        return {
            'type': 'SpeckleReject',
            'minAllowedAreaPercent': self.minAllowedAreaPercent
        }

class DisplayContours(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.window = settings.get('window', 'contours')
        self.enabled = settings.get('enabled', True)
        cv.namedWindow(self.window)
    
    def run(self, input):
        if self.enabled:
            contoursImg = np.zeros((input.img.shape[0], input.img.shape[1], 3), dtype=np.uint8)
            color = (255, 0, 0)

            for i in range(len(input.contours)):
                cv.drawContours(contoursImg, input.cvContours, i, color, 2, cv.LINE_8, input.hierarchy, 0)

            cv.imshow(self.window, contoursImg)

        return input

    def dump(self):
        return {
            'type': 'DisplayContours',
            'window': self.window
        }

import cv2 as cv
import numpy as np
from pipelines.base import Base
from pipelines.result import Result
from pipelines.display_image import DisplayImage

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

class FindContoursResult(Result):
    def __init__(self, input, cvContours, hierarchy = None):
        super().__init__(input)
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
            
    def run(self, input):
        if self.enabled:
            res = cv.findContours(input.img, cv.RETR_EXTERNAL, cv.CHAIN_APPROX_TC89_KCOS)
            contours = res[0]
            hierarchy = res[1]
            return FindContoursResult(input, contours, hierarchy)
        else:
            return input

    def dump(self):
        return {
            'type': 'FindContours',
            'enabled': self.enabled
        }

class SpeckleRejectResult(Result):
    def __init__(self, input, contours):
        super().__init__(input)
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
            return SpeckleRejectResult(input, filteredContours)
        else:
            return input
            
    def gui(self, window):
        if self.enabled:
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
            'minAllowedAreaPercent': self.minAllowedAreaPercent,
            'enabled': self.enabled
        }

class DisplayContours(DisplayImage):
    def __init__(self, settings = {}):
        super().__init__(settings)
        self.type = 'DisplayContours'
    
    def run(self, input):
        if self.enabled:
            canvas = self._getImageToDisplay(input, copy = True)
            color = (255, 0, 0)

            for i in range(len(input.contours)):
                cv.drawContours(
                    canvas,
                    input.cvContours,
                    i,
                    color,
                    2,
                    cv.LINE_8,
                    input.hierarchy,
                    0)

            cv.imshow(self.window, canvas)

        return input

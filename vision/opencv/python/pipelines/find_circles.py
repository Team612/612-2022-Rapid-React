from math import sqrt
from multiprocessing.sharedctypes import Value
import cv2 as cv
import numpy as np
from pipelines.base import Base

class Circle:
    def __init__(self, contour, center, radius):
        self.contour = contour
        self.center = center
        self.radius = radius

class FindCirclesResult:
    def __init__(self, settings, input, circles):
        self.settings = settings
        self.input = input
        self.img = input.img
        self.circles = circles

class FindCircles(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.minRadius = settings.get('minRadius', 0)
        self.maxRadius = settings.get('maxRadius', 100)
        self.minDist = settings.get('minDist', 20)
        self.maxCannyThresh = settings.get('maxCannyThresh', 90)
        self.accuracy = settings.get('accuracy', 20)
        self.allowableThreshold = settings.get('allowableThreshold', 5)
        self.enabled = settings.get('enabled', True)
            
    def run(self, input):
        if not self.enabled:
            return input

        diag = sqrt(input.img.shape[0] * input.img.shape[0] + input.img.shape[1] * input.img.shape[1])
        minRadius = int(self.minRadius * diag / 100.0)
        maxRadius = int(self.maxRadius * diag / 100.0)

        circles = cv.HoughCircles(
            input.img,
            cv.HOUGH_GRADIENT,
            dp = 1.0,
            minDist = self.minDist,
            param1 = self.maxCannyThresh,
            param2 = self.accuracy,
            minRadius = minRadius,
            maxRadius = maxRadius)
        unmatchedContours = input.contours.copy()
        results = []

        if not isinstance(circles, list):
            circles = []

        for x in range(len(circles)):
            # Grab the current circle we are looking at
            c = circles[x]
            # Find the center points of that circle
            x_center = c[0]
            y_center = c[1]

            for contour in unmatchedContours:
                # Grab the moments of the current contour
                mu = contour.getMoments()
                # Determine if the contour is within the threshold of the detected circle
                # NOTE: This means that the centroid of the contour must be within the "allowable
                # threshold"
                # of the center of the circle
                if abs(x_center - (mu.m10 / mu.m00)) <= self.allowableThreshold \
                    and abs(y_center - (mu.m01 / mu.m00)) <= self.allowableThreshold:
                    # If it is, then add it to the output array
                    results.add(Circle(contour, (c[0], c[1]), c[2]))
                    unmatchedContours.remove(contour)
                    break

        return FindCirclesResult(self.settings, input, results)

    def gui(self, window):
        cv.createTrackbar(
            'Min radius %',
            window,
            self.minRadius,
            100,
            lambda value: self._set_minRadius(value))
        cv.createTrackbar(
            'Max radius %',
            window,
            self.maxRadius,
            100,
            lambda value: self._set_maxRadius(value))
        cv.createTrackbar(
            'Min distance',
            window,
            self.minDist,
            100,
            lambda value: self._set_minDist(value))
        cv.createTrackbar(
            'Max Canny threshold',
            window,
            self.maxCannyThresh,
            100,
            lambda value: self._set_maxCannyThresh(value))
        cv.createTrackbar(
            'Accuracy',
            window,
            self.accuracy,
            100,
            lambda value: self._set_accuracy(value))
        cv.createTrackbar(
            'Allowable threshold',
            window,
            self.allowableThreshold,
            100,
            lambda value: self._set_allowableThreshold(value))
        # self.accuracy = settings.get('accuracy', 20)
        # self.allowableThreshold = settings.get('allowableThreshold', 5)

    def _set_minRadius(self, value):
        self.minRadius = value

    def _set_maxRadius(self, value):
        self.maxRadius = value

    def _set_minDist(self, value):
        if value > 0:
            self.minDist = value

    def _set_maxCannyThresh(self, value):
        if value > 0:
            self.maxCannyThresh = value

    def _set_accuracy(self, value):
        if value > 0:
            self.accuracy = value

    def _set_allowableThreshold(self, value):
        self.allowableThreshold = value

    def dump(self):
        return {
            'type': 'FindCircles',
            'minRadius': self.minRadius,
            'maxRadius': self.maxRadius,
            'minDist': self.minDist,
            'maxCannyThresh': self.maxCannyThresh,
            'accuracy': self.accuracy,
            'allowableThreshold': self.allowableThreshold
        }

class DisplayCircles(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.window = settings.get('window', 'circles')
        self.enabled = settings.get('enabled', True)
        cv.namedWindow(self.window)
    
    def run(self, input):
        if self.enabled:
            canvas = np.zeros((input.img.shape[0], input.img.shape[1], 3), dtype=np.uint8)
            color = (255, 0, 0)

            cvContours = [c for c in input.circles]
            for i in range(len(cvContours)):
                cv.drawContours(canvas, cvContours, i, color, 2, cv.LINE_8, input.hierarchy, 0)

            cv.imshow(self.window, canvas)
        return input

    def dump(self):
        return {
            'type': 'DisplayCircles',
            'window': self.window
        }

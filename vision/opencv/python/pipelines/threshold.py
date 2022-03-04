import cv2 as cv
from pipelines.base import Base

class Threshold(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.method = settings.get('method', 'RANGE')
        self.min = settings.get('min', [ 0, 0, 0 ])
        self.max = settings.get('max', [ 255, 255, 255 ])
        self.invert = settings.get('invert', True)
        self.enabled = settings.get('enabled', True)
    
    def run(self, input):
        if self.enabled:
            thresh = cv.THRESH_BINARY_INV if self.invert else cv.THRESH_BINARY
            
            if self.method == 'RANGE':
                return input.next(self._getMaskFromRange(input))
            elif self.method == 'RANGE_AND':
                mask = self._getMaskFromRange(input)
                return input.next(cv.bitwise_and(input.img, input.img, mask = mask))
            elif self.method == 'OTSU':
                ret, timg = cv.threshold(input.img, 0, 255, thresh + cv.THRESH_OTSU)
                return input.next(timg)
            elif self.method == 'TRIANGLE':
                ret, timg = cv.threshold(input.img, 0, 255, thresh + cv.THRESH_TRIANGLE)
                return input.next(timg)
            elif self.method == 'ADAPTIVE_THRESH_MEAN':
                timg = cv.adaptiveThreshold(
                    input.img,
                    255,
                    cv.ADAPTIVE_THRESH_MEAN_C,
                    thresh,
                    11,
                    2)
                return input.next(timg)
            elif self.method == 'ADAPTIVE_THRESH_GAUSSIAN':
                timg = cv.adaptiveThreshold(
                    input.img,
                    255,
                    cv.ADAPTIVE_THRESH_GAUSSIAN_C,
                    thresh,
                    11,
                    2)
                return input.next(timg)
        return input

    def _getMaskFromRange(self, input):
        return cv.inRange(
            input.img,
            (self.min[0], self.min[1], self.min[2]),
            (self.max[0], self.max[1], self.max[2]))

    def gui(self, window):
        if self.enabled:
            cv.createTrackbar('ch 0 min', window, self.min[0], 255, lambda value: self._setMin(0, value))
            cv.createTrackbar('ch 0 max', window, self.max[0], 255, lambda value: self._setMax(0, value))
            cv.createTrackbar('ch 1 min', window, self.min[1], 255, lambda value: self._setMin(1, value))
            cv.createTrackbar('ch 1 max', window, self.max[1], 255, lambda value: self._setMax(1, value))
            cv.createTrackbar('ch 2 min', window, self.min[2], 255, lambda value: self._setMin(2, value))
            cv.createTrackbar('ch 2 max', window, self.max[2], 255, lambda value: self._setMax(2, value))

    def _setMin(self, idx, value):
        self.min[idx] = value

    def _setMax(self, idx, value):
        self.max[idx] = value

    def dump(self):
        return {
            'type': 'Threshold',
            'method': self.method,
            'min': self.min,
            'max': self.max,
            'invert': self.invert,
            'enabled': self.enabled
        }

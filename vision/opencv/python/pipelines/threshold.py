import cv2 as cv
from pipelines.base import Base

class Threshold(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.min = settings.get('min', [ 0, 0, 0 ])
        self.max = settings.get('max', [ 255, 255, 255 ])
        self.enabled = settings.get('enabled', True)
    
    def run(self, input):
        if self.enabled:
            return input.next(
                cv.inRange(
                    input.img,
                    (self.min[0], self.min[1], self.min[2]),
                    (self.max[0], self.max[1], self.max[2])))
        else:
            return input

    def gui(self, window):
        cv.createTrackbar('H min', window, self.min[0], 255, lambda value: self._setMin(0, value))
        cv.createTrackbar('H max', window, self.max[0], 255, lambda value: self._setMax(0, value))
        cv.createTrackbar('S min', window, self.min[1], 255, lambda value: self._setMin(1, value))
        cv.createTrackbar('S max', window, self.max[1], 255, lambda value: self._setMax(1, value))
        cv.createTrackbar('V min', window, self.min[2], 255, lambda value: self._setMin(2, value))
        cv.createTrackbar('V max', window, self.max[2], 255, lambda value: self._setMax(2, value))

    def _setMin(self, idx, value):
        self.min[idx] = value

    def _setMax(self, idx, value):
        self.max[idx] = value

    def dump(self):
        return {
            'type': 'Threshold',
            'min': self.min,
            'max': self.max,
            'enabled': self.enabled
        }

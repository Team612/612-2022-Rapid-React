import cv2 as cv
import numpy as np
from pipelines.base import Base

class Morph(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.kernel = np.ones((5, 5), np.uint8)
        self.operation = settings.get('operation', 'open')
        self.enabled = settings.get('enabled', True)

    def run(self, input):
        if self.enabled and self.operation == 'open':
            return input.next(cv.morphologyEx(input.img, cv.MORPH_OPEN, self.kernel))
        else:
            return input

    def gui(self, window):
        if self.enabled:
            cv.createTrackbar(
                'Morph Open',
                window,
                1 if self.operation == 'open' else 0,
                1,
                lambda value: self._setOperationFromGUI(value))
    
    def _setOperationFromGUI(self, value):
        if value == 1:
            self.operation = 'open'
        else:
            self.operation = None

    def dump(self):
        return {
            'type': 'Morph',
            'operation': self.operation,
            'enabled': self.enabled
        }

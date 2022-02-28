import cv2 as cv
import numpy as np
from pipelines.base import Base

class Morphology(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.kernel = np.ones((5, 5), np.uint8)
        self.operation = settings.get('operation', 'open')
        self.enabled = settings.get('enabled', True)

    def run(self, img):
        if self.enabled and self.operation == 'open':
            return cv.morphologyEx(img, cv.MORPH_OPEN, self.kernel)
        else:
            return img

    def gui(self, window):
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
            'type': 'Morphology',
            'operation': self.operation
        }

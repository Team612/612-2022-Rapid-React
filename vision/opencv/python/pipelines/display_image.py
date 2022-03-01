import cv2 as cv
import numpy as np
from pipelines.base import Base

class DisplayImage(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.window = settings.get('window', 'circles')
        self.image = settings.get('image', 'input')
        self.enabled = settings.get('enabled', True)

        # if self.enabled:
        #     cv.namedWindow(self.window)
    
    def run(self, input):
        if self.enabled:
            cv.imshow(self.window, self._getImageToDisplay(input))
        return input

    def dump(self):
        return {
            'type': 'DisplayImage',
            'window': self.window,
            'image': self.image,
            'enabled': self.enabled
        }

    def _getImageToDisplay(self, input, copy = False):
        if self.image == 'input':
            return input.img.copy() if copy else input.img
        elif self.image == 'first':
            return input.first.img.copy() if copy else input.first.img
        else:
            return np.zeros((input.img.shape[0], input.img.shape[1], 3), dtype=np.uint8)

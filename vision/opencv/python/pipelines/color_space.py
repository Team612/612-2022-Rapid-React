import cv2 as cv
from pipelines.base import Base

class ColorSpace(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.to = settings.get('to', 'HSV')
        self.enabled = settings.get('enabled', True)
    
    def run(self, input):
        if self.enabled:
            if self.to == 'HSV':
                return input.next(cv.cvtColor(input.img, cv.COLOR_BGR2HSV, 3))
            elif self.to == 'GRAY':
                return input.next(cv.cvtColor(input.img, cv.COLOR_BGR2GRAY, 1))

        return input

    def dump(self):
        return {
            'type': 'ColorSpace',
            'to': self.to,
            'enabled': self.enabled
        }
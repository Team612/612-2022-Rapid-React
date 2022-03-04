import cv2 as cv
from pipelines.base import Base

class ColorSpace(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.to = settings.get('to', 'COLOR_BGR2HSV')
        self.channels = settings.get('channels', 0)
        self.enabled = settings.get('enabled', True)

        if self.to == 'HSV':
            self.to = 'COLOR_BGR2HSV'
        elif self.to == 'GRAY':
            self.to = 'COLOR_BGR2GRAY'
    
    def run(self, input):
        if self.enabled:
            if self.to == 'HSV2GRAY':
                (h, s, v) = cv.split(input.img)
                return input.next(v)
            else:
                return input.next(cv.cvtColor(input.img, getattr(cv, self.to), self.channels))
        else:
            return input

    def dump(self):
        return {
            'type': 'ColorSpace',
            'to': self.to,
            'channels': self.channels,
            'enabled': self.enabled
        }
import cv2 as cv
from pipelines.base import Base

class ConvertToHSV(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.enabled = settings.get('enabled', True)
    
    def run(self, input):
        if self.enabled:
            # TODO: Support other color spaces?
            return input.next(cv.cvtColor(input.img, cv.COLOR_BGR2HSV, 3))
        else:
            return input

    def dump(self):
        return {
            'type': 'ConvertToHSV',
            'enabled': self.enabled
        }
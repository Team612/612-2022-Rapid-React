import cv2 as cv
from pipelines.base import Base

class ConvertToHSV(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.enabled = settings.get('enabled', True)
    
    def run(self, img):
        if self.enabled:
            # TODO: Support other color spaces?
            return cv.cvtColor(img, cv.COLOR_BGR2HSV, 3)
        else:
            return img

    def dump(self):
        return { 'type': 'ConvertToHSV' }
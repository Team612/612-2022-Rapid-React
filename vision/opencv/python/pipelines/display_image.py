import cv2 as cv
from pipelines.base import Base

class DisplayImage(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.window = settings.get('window', 'image')
        self.enabled = settings.get('enabled', True)
        cv.namedWindow(self.window)
    
    def run(self, img):
        if self.enabled:
            cv.imshow(self.window, img)
        return img

    def dump(self):
        return {
            'type': 'DisplayImage',
            'window': self.window
        }

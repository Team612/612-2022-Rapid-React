import cv2 as cv
from pipelines.base import Base

class Blur(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.method = settings.get('method', 'GAUSSIAN')
        self.d = settings.get('d', 5)
        self.sigmaColor = settings.get('sigmaColor', 75)
        self.sigmaSpace = settings.get('sigmaSpace', 75)
        self.enabled = settings.get('enabled', True)

    def run(self, input):
        if self.enabled:
            if self.method == 'GAUSSIAN':
                return input.next(cv.GaussianBlur(input.img, (5, 5), 0))
            elif self.method == 'MEDIAN':
                return input.next(cv.medianBlur(input.img, 5))
            elif self.method == 'BILATERAL':
                return input.next(cv.bilateralFilter(
                    input.img,
                    self.d,
                    self.sigmaColor,
                    self.sigmaSpace))

        return input

    def dump(self):
        return {
            'type': 'Blur',
            'method': self.method,
            'd': self.d,
            'sigmaColor': self.sigmaColor,
            'sigmaSpace': self.sigmaSpace,
            'enabled': self.enabled
        }
    
    def gui(self, window):
        if self.enabled:
            if self.method == 'BILATERAL':
                # See https://docs.opencv.org/3.4/d4/d13/tutorial_py_filtering.html
                cv.createTrackbar('blur d', window, self.d, 10, lambda value: self._set_d(value))
                cv.createTrackbar(
                    'sigmaColor',
                    window,
                    self.sigmaColor,
                    255,
                    lambda value: self._set_sigmaColor(value))
                cv.createTrackbar(
                    'sigmaSpace',
                    window,
                    self.sigmaSpace,
                    255,
                    lambda value: self._set_sigmaSpace(value))
   
    def _set_d(self, value):
        if value > 0:
            self.d = value
    
    def _set_sigmaColor(self, value):
        if value > 0:
            self.sigmaColor = value
    
    def _set_sigmaSpace(self, value):
        if value > 0:
            self.sigmaSpace = value

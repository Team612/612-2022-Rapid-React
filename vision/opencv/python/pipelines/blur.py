import cv2 as cv
from pipelines.base import Base

# See https://docs.opencv.org/3.4/d4/d13/tutorial_py_filtering.html

class Blur(Base):
    def __init__(self, settings = {}):
        self.settings = settings
        self.method = settings.get('method', 'GAUSSIAN')
        self.kernelSize = settings.get('kernelSize', 5)
        self.sigmaX = settings.get('sigmaX', 0)
        self.sigmaY = settings.get('sigmaY', 0)
        self.d = settings.get('d', 5)
        self.sigmaColor = settings.get('sigmaColor', 75)
        self.sigmaSpace = settings.get('sigmaSpace', 75)
        self.enabled = settings.get('enabled', True)

    def run(self, input):
        if self.enabled:
            if self.method == 'GAUSSIAN':
                return input.next(
                    cv.GaussianBlur(
                        input.img,
                        (self.kernelSize, self.kernelSize),
                        self.sigmaX,
                        self.sigmaY))
            elif self.method == 'MEDIAN':
                return input.next(cv.medianBlur(input.img, self.kernelSize))
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
            'kernelSize': self.kernelSize,
            'sigmaX': self.sigmaX,
            'sigmaY': self.sigmaY,
            'd': self.d,
            'sigmaColor': self.sigmaColor,
            'sigmaSpace': self.sigmaSpace,
            'enabled': self.enabled
        }
    
    def gui(self, window):
        if self.enabled:
            if self.method == 'BILATERAL':
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
            elif self.method == 'GAUSSIAN' or self.method == 'MEDIAN':
                cv.createTrackbar('kernelSize', window, self.kernelSize, 5, lambda value: self._set_kernelSize(value))
   
            if self.method == 'GAUSSIAN':
                cv.createTrackbar('sigmaX', window, self.sigmaX, 255, lambda value: self._set_sigmaX(value))
                cv.createTrackbar('sigmaY', window, self.sigmaY, 255, lambda value: self._set_sigmaY(value))
                
    def _set_kernelSize(self, value):
        if value > 0 and value % 2 == 1:
            self.kernelSize = value
   
    def _set_sigmaX(self, value):
        self.sigmaX = value
   
    def _set_sigmaY(self, value):
        self.sigmaY = value
   
    def _set_d(self, value):
        if value > 0:
            self.d = value
    
    def _set_sigmaColor(self, value):
        if value > 0:
            self.sigmaColor = value
    
    def _set_sigmaSpace(self, value):
        if value > 0:
            self.sigmaSpace = value

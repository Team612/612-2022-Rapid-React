import cv2 as cv
from pipelines.base import Base
from pipelines.factory import createPipeline

class Chain(Base):
    def __init__(self, settings = {}):
        self.steps = [createPipeline(s) for s in settings.get('steps', [])]
        self.enabled = settings.get('enabled', True)
        self.printFPS = settings.get('printFPS', True)

        if self.printFPS:
            self.frames = 0
            self.t1 = None

    def add(self, s):
        self.steps.add(s)
    
    def run(self, input):
        if not self.enabled:
            return input
            
        if self.printFPS:
            if self.frames == 0:
                self.t1 = cv.getTickCount()

            self.frames += 1

        result = input

        for s in self.steps:
            result = s.run(result)

        if self.printFPS:
            t2 = cv.getTickCount()
            t = (t2 - self.t1) / cv.getTickFrequency()

            if t >= 2.0:
                print('{0} FPS'.format(self.frames / t))
                self.frames = 0

        return result

    def gui(self, window):
        for s in self.steps:
            s.gui(window)

    def dump(self):
        return {
            'type': 'Chain',
            'steps': [s.dump() for s in self.steps],
            'printFPS': self.printFPS
        }
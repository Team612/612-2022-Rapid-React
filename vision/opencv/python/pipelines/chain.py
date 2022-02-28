from pipelines.base import Base

class Chain(Base):
    def __init__(self, steps = [], settings = {}):
        self.steps = steps
        self.enabled = settings.get('enabled', True)

    def add(self, s):
        self.steps.add(s)
    
    def run(self, input):
        if self.enabled:
            # TODO: calc FPS

            result = input

            for s in self.steps:
                result = s.run(result)

            return result
        else:
            return input

    def gui(self, window):
        for s in self.steps:
            s.gui(window)

    def dump(self):
        return [s.dump() for s in self.steps]
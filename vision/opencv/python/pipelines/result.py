class Result:
    def __init__(self, prev = None):
        self.prev = prev
        self.img = prev.img if prev is not None else None
    
    @classmethod
    def start(cls, img):
        r = Result()
        r.img = img
        return r

    def next(self, img):
        r = Result(self)
        r.img = img
        return r
    
    @property
    def first(self):
        n = self

        while n.prev is not None:
            n = n.prev

        return n
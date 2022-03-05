import json
from importlib import import_module


def createPipeline(p):
    typeParts = p['type'].split('.')
    moduleName = '.'.join(typeParts[0:-1])
    
    if len(moduleName) == 0:
        moduleName = 'pipelines.index'

    className = typeParts[-1]
    module = import_module(moduleName)
    cls = getattr(module, className)
    return cls(p)

def load(fileName, settings = {}):
    pipeline = json.load(open(fileName))

    if isinstance(pipeline, list):
        return createPipeline(settings | { 'type': 'Chain', 'steps': pipeline })
    elif 'type' in pipeline:
        return createPipeline(settings | pipeline)
    else:
        return None

def save(pipeline, fileName):
    json.dump(pipeline.dump(), open(fileName, 'w'))
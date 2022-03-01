from asyncio.windows_utils import pipe
import json

from pipelines.chain import Chain
from pipelines.color_space import ConvertToHSV
from pipelines.contours import FindContours, SpeckleReject, DisplayContours
from pipelines.morph import Morph
from pipelines.threshold import Threshold
from pipelines.display_image import DisplayImage
from pipelines.find_circles import FindCircles, DisplayCircles

types = {
    'Chain': Chain,
    'ConvertToHSV': ConvertToHSV,
    'DisplayImage': DisplayImage,
    'DisplayCircles': DisplayCircles,
    'DisplayContours': DisplayContours,
    'FindCircles': FindCircles,
    'FindContours': FindContours,
    'Morph': Morph,
    'Morphology': Morph,
    'SpeckleReject': SpeckleReject,
    'Threshold': Threshold
}

def load(fileName):
    pipeline = json.load(open(fileName))

    if isinstance(pipeline, list):
        return Chain([types[p['type']](p) for p in pipeline])
    else:
        return None

def save(pipeline, fileName):
    json.dump(pipeline.dump(), open(fileName, 'w'))
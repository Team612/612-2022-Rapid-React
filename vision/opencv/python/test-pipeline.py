import cv2 as cv
import argparse
import pipelines.factory

parser = argparse.ArgumentParser()
parser.add_argument('-f', '--file', required=True)
parser.add_argument('-d', '--device', type=int, default=0)
parser.add_argument('-g', '--gui', action='store_const', const=True, default=False)
args = parser.parse_args()

# TODO: pass in video device number
cap = cv.VideoCapture(args.device)

if not cap.isOpened():
    print("Cannot open camera")
    exit()

pipeline = pipelines.factory.load(args.file)

if args.gui:
    cv.namedWindow('params')
    pipeline.gui('params')

while True:
    ret, frame = cap.read()

    if not ret:
        print("Can't receive frame (stream end?). Exiting ...")
        break
    
    result = pipeline.run(frame)
    
    key = cv.waitKey(1)

    if key == ord('q'):
        break
    elif key == ord('s'):
        pipelines.factory.save(pipeline, 'saved.json')

cap.release()
cv.destroyAllWindows()
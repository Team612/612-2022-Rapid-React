import cv2 as cv
import argparse
import pipelines.factory
from pipelines.result import Result
from flask import Flask, render_template, Response

parser = argparse.ArgumentParser()
parser.add_argument('-f', '--file', required=True)
parser.add_argument('-d', '--device', type=int, default=0)
parser.add_argument('-g', '--gui', action='store_const', const=True, default=False)
args = parser.parse_args()

camera = cv.VideoCapture(args.device)

if not camera.isOpened():
    print("Cannot open camera")
    exit()

pipeline = pipelines.factory.load(args.file)

if args.gui:
    cv.namedWindow('params')
    pipeline.gui('params')

# while True:
#     ret, frame = camera.read()

#     if not ret:
#         print("Can't receive frame (stream end?). Exiting ...")
#         break
    
#     result = pipeline.run(Result.start(frame))
    
#     key = cv.waitKey(1)

#     if key == ord('q'):
#         break
#     elif key == ord('s'):
#         pipelines.factory.save(pipeline, 'saved.json')

def gen_frames():  # generate frame by frame from camera
    while True:
        # Capture frame-by-frame
        success, frame = camera.read()  # read the camera frame
        if not success:
            break
        else:
            result = pipeline.run(Result.start(frame))
            ret, buffer = cv.imencode('.jpg', result.img)
            res = buffer.tobytes()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + res + b'\r\n')  # concat frame one by one and show result

app = Flask(__name__)

@app.route('/video_feed')
def video_feed():
    #Video streaming route. Put this in the src attribute of an img tag
    return Response(gen_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/')
def index():
    """Video streaming home page."""
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)

camera.release()
cv.destroyAllWindows()
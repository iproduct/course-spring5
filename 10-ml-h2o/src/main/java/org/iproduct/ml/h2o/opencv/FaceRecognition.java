package org.iproduct.ml.h2o.opencv;

import hex.genmodel.easy.exception.PredictException;
import lombok.extern.slf4j.Slf4j;
import nu.pattern.OpenCV;
import org.iproduct.ml.h2o.domain.RecognitionResult;
import org.iproduct.ml.h2o.services.DnnRecognizerService;
import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

@Slf4j
class FaceRecognitionRun {
//    static {
//        System.loadLibrary("opencv_java450");
//    }
    public static final double CONFIDENCE_TRESHOLD = 0.5;
//    public static final String TEST_IMAGE_FILE =  "src/main/resources/data/messi5.jpg";
//    public static final String TEST_IMAGE_FILE =  "src/main/resources/data/trayan7.jpg";
    public static final String TEST_IMAGE_FILE =  "src/main/resources/data/4b1f51c4499c1088b759cdbe30b755e117bd0afa.jpg";

    public void run() {
        // load our serialized face detector from disk
        System.out.println("[INFO] loading face detector...");
        String protoPath = "src/main/resources/face_detection_model/deploy.prototxt";
        String modelPath = "src/main/resources/face_detection_model/res10_300x300_ssd_iter_140000.caffemodel";
        Net detector = Dnn.readNetFromCaffe(protoPath, modelPath);

        // load our serialized face embedding model from disk
        System.out.println("[INFO] loading face recognizer...");
        String embeddingsModel = "src/main/resources/openface_nn4.small2.v1.t7";
        Net embedder = Dnn.readNetFromTorch(embeddingsModel);

        //! [load image]
        Mat frame = Imgcodecs.imread(TEST_IMAGE_FILE );
        if (frame.empty() == true) {
            System.out.println("Error loading src1");
            return;
        }
        int height = frame.size(0);
        int width = frame.size(1);
        Mat resized = new Mat();
        Imgproc.resize(frame, resized, new Size(300, 300));

        // construct a blob from the image
        Mat imageBlob = Dnn.blobFromImage(resized, 1.0, new Size(300, 300),
                new Scalar(104.0, 177.0, 123.0), false, false);
        // apply OpenCV's deep learning-based face detector to localize
        // faces in the input image
        detector.setInput(imageBlob);
        Mat detections = detector.forward();
        System.out.printf("Results: %s\n", detections.size(2));
//        double[] results = new double[7];
//        detections.get(new int[]{0, 0, 0}, results);
//        System.out.printf("Mat: %s\n", results);
//                new Range[]{new Range(0,1), new Range(0,1), new Range(0,1), new Range(2,7)}));

        // loop over the detections
        for (int i = 0; i < detections.size(2); i++) {
            // extract the confidence (i.e., probability) associated with the prediction
            double confidence = detections.get(new int[]{0, 0, i, 2})[0];
//            System.out.printf("Confidence: %s\n", confidence);

            // filter out weak detections
            if (confidence > CONFIDENCE_TRESHOLD) {
                // compute the (x, y)-coordinates of the bounding box for the face
                int startX = (int) (detections.get(new int[]{0, 0, i, 3})[0] * width);
                int startY = (int) (detections.get(new int[]{0, 0, i, 4})[0] * height);
                int endX = (int) (detections.get(new int[]{0, 0, i, 5})[0] * width);
                int endY = (int) (detections.get(new int[]{0, 0, i, 6})[0] * height);
                Imgproc.rectangle(
                        frame,                                  //Matrix obj of the image
                        new Point(startX, startY),        //p1
                        new Point(endX, endY),            //p2
                        new Scalar(0, 0, 255),                  //Scalar object for color
                        2                               //Thickness of the line
                );
                // ensure the face width and height are sufficiently large
                if(endX - startX < 20 ||  endY - startY < 20) {
                    continue;
                }

                // extract the face ROI
                Mat face = frame.submat(startY, endY, startX, endX);

                // construct a blob for the face ROI, then pass the blob
                // through our face embedding model to obtain the 128-d
                // quantification of the face
                Mat faceBlob = Dnn.blobFromImage(face, 1.0 / 255, new Size(96, 96),
                        new Scalar(0, 0, 0), true, false);

                embedder.setInput(faceBlob);
                Mat embeddingsMat = embedder.forward();
                System.out.printf("Mat: %s\n", embeddingsMat);

                float[] embeddings = new float[128];
                embeddingsMat.get(0,0, embeddings);
//                System.out.printf("Array: %s\n", Arrays.toString(embeddings));

                // perform classification to recognize the face
                DnnRecognizerService recognizer = new DnnRecognizerService();
                recognizer.init();
                try {
                    RecognitionResult result = recognizer.recognize(embeddings);
                    System.out.printf("Recognition result: %s: %f%n", result, result.getClassProbabilites()[result.getLabelIndex()]);
                } catch (PredictException e) {
                    log.error("Error predicting embedding: ", e);
                }
            }
        }

        //![display]
        HighGui.imshow("Result", frame);
        HighGui.waitKey(0);
        //![display]

        System.exit(0);
    }
}

public class FaceRecognition {
    public static void main(String[] args) {
        // Load the native library.
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        OpenCV.loadShared();
        new FaceRecognitionRun().run();
    }
}

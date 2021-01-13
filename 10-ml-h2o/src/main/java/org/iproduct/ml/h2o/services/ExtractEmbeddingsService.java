package org.iproduct.ml.h2o.services;

import hex.genmodel.easy.exception.PredictException;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.ml.h2o.domain.Embeddings;
import org.iproduct.ml.h2o.domain.RecognitionResult;
import org.iproduct.ml.h2o.domain.Resource;
import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_COLOR;

@Service
@Slf4j
public class ExtractEmbeddingsService {
    public static final String PROTO_PATH = "src/main/resources/face_detection_model/deploy.prototxt";
    public static final String MODEL_PATH = "src/main/resources/face_detection_model/res10_300x300_ssd_iter_140000.caffemodel";
    public static final String EMBEDDINGS_MODEL = "src/main/resources/openface_nn4.small2.v1.t7";
    public static final double CONFIDENCE_TRESHOLD = 0.5;
    //    public static final String TEST_IMAGE_FILE =  "src/main/resources/data/messi5.jpg";
    public static final String TEST_IMAGE_FILE = "src/main/resources/data/trayan7.jpg";

    private Net detector;
    private Net embedder;

    @Autowired
    private DnnRecognizerService dnnRecognizerService;

    @PostConstruct
    public void init() {


//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // load our serialized face detector from disk
        log.info("Loading face detector...");
        detector = Dnn.readNetFromCaffe(PROTO_PATH, MODEL_PATH);

        // load our serialized face embedding model from disk
        log.info("Loading face recognizer...");
        embedder = Dnn.readNetFromTorch(EMBEDDINGS_MODEL);

    }

    public synchronized Embeddings extractEmbeddings(Resource resource) {
//        Scanner sc = null;
//        try {
//            sc = new Scanner(new File("src/main/resources/data/imageUrlEncoded.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        String image = sc.nextLine();
//        final String dataUrl = image;

        final String dataUrl = resource.getUrl();
        final int dataStartIndex = dataUrl.indexOf(",") + 1;
        final String data = dataUrl.substring(dataStartIndex);
        byte[] imageBytes = java.util.Base64.getDecoder().decode(data);
        Mat imgf = new Mat(1, imageBytes.length, CvType.CV_8U);
        imgf.put(0, 0, imageBytes);
        Mat frame = Imgcodecs.imdecode(imgf, IMREAD_COLOR);

        if (frame.empty() == true) {
            System.out.println("Error loading src1");
            return null;
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
                if (endX - startX < 20 || endY - startY < 20
                        || startX <0 || startY < 0
                        || endX > width || endY > height) {
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
                embeddingsMat.get(0, 0, embeddings);
//                System.out.printf("Array: %s\n", Arrays.toString(embeddings));

                return new Embeddings(embeddings);
            }
        }

        //![display]
//        HighGui.imshow("Result", frame);
//        HighGui.waitKey(0);

        return null;
    }
}

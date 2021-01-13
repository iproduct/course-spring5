package org.iproduct.ml.h2o;

import hex.genmodel.easy.exception.PredictException;
import nu.pattern.OpenCV;
import org.iproduct.ml.h2o.domain.Resource;
import org.iproduct.ml.h2o.services.ExtractEmbeddingsService;
import org.opencv.core.Core;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // load opencv native library
        OpenCV.loadShared();
        SpringApplication.run(Application.class, args);
    }

}

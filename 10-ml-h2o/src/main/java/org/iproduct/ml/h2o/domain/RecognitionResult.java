package org.iproduct.ml.h2o.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecognitionResult {
    private String label = "Not recognized";
    private int labelIndex;
    private double confidence;
    private double[] classProbabilites;
}

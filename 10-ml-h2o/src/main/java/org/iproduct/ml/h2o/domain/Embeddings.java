package org.iproduct.ml.h2o.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Embeddings {
    private float[] embeddings;
}

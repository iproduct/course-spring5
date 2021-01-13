package org.iproduct.ml.h2o.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private String url;
    private String mime;
    private int width;
    private int height;
}

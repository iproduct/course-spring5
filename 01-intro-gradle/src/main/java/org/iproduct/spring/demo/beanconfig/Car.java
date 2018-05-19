package org.iproduct.spring.demo.beanconfig;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car {
    private String make;
    private int model;
    private Engine engine;
    private int horsePower;
    private int yearOfProduction;
}

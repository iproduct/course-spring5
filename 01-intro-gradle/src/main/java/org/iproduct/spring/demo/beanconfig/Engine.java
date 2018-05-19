package org.iproduct.spring.demo.beanconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
public class Engine {
    private int capacity;
    private int horsePower;
    private int numberOfCylinders;
    private String model;
}
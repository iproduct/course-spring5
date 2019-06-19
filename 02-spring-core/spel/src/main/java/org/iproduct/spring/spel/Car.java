package org.iproduct.spring.spel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Car {
    @NonNull
    private String make;
    @NonNull
    private String model;
    private Engine engine;
    @NonNull
    private int horsePower;
    @NonNull
    private int yearOfProduction;
}

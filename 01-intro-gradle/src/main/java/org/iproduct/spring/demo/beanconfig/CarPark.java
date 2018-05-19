package org.iproduct.spring.demo.beanconfig;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarPark {
    List<Car> cars = new ArrayList<>();
}

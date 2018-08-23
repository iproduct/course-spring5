package org.iproduct.spring.spel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPark {
    List<Car> cars = new ArrayList<>();
    public static CarPark create(List<Car> cars) {
        return new CarPark(cars);
    };
}

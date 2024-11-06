package course.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPark {
    private List<Car> cars = new ArrayList<>();
    public static CarPark create(List<Car> cars) {
        return new CarPark(cars);
    };
}

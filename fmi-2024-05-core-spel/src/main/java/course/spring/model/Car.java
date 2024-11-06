package course.spring.model;

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
    private Integer horsePower;
    @NonNull
    private Integer yearOfProduction;
}

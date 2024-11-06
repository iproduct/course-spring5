package course.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Engine {
    private int capacity;
    private int horsePower;
    private int numberOfCylinders;
    private String model;
}

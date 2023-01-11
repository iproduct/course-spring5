package course.springdate.jpaintro.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "car")
@Table(name="cars")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Car extends Vehicle {
    @NonNull
    private int seats;

    @OneToOne(mappedBy = "car")
    private PlateNumber plate;

    public Car(Long id, @NonNull String model, @NonNull BigDecimal price, @NonNull String fuelType, @NonNull int seats) {
        super(id, model, price, fuelType);
        this.seats = seats;
    }

    public Car(@NonNull String model, @NonNull BigDecimal price, @NonNull String fuelType, @NonNull int seats) {
        super(model, price, fuelType);
        this.seats = seats;
    }
}

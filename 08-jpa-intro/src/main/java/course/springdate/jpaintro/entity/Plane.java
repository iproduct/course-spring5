package course.springdate.jpaintro.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="planes")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Plane extends Vehicle {
    @NonNull
    private Integer passengerCapacity;

    public Plane(@NonNull String model, @NonNull BigDecimal price, @NonNull String fuelType, @NonNull int passengerCapacity) {
        super(model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }

    public Plane(Long id, @NonNull String model, @NonNull BigDecimal price, @NonNull String fuelType, @NonNull int passengerCapacity) {
        super(id, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }
}

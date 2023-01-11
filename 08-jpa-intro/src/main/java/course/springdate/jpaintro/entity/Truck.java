package course.springdate.jpaintro.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="trucks")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Truck extends Vehicle {
    @NonNull
    @Column(name = "load_capacity")
    private Double loadCapacity;

    public Truck(@NonNull String model, @NonNull BigDecimal price, @NonNull String fuelType, @NonNull double loadCapacity) {
        super(model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }

    public Truck(Long id, @NonNull String model, @NonNull BigDecimal price, @NonNull String fuelType, @NonNull double loadCapacity) {
        super(id, model, price, fuelType);
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Truck{");
        sb.append("loadCapacity=").append(loadCapacity);
        sb.append('}');
        return sb.toString();
    }
}

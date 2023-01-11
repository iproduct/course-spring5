package course.springdate.jpaintro.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Table(name="vehicles")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @NonNull
//    private String type;
    @NonNull
    private String model;
    @NonNull
    private BigDecimal price;
    @NonNull
    @Column(name = "fuel_type") // -> BD
    private String fuelType;
//    @Column(name = "registration_date")
//    private Date registrationDate = new Date();
}

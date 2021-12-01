package demos.spring.vehicles.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Offer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;


    @NotNull(message = "Vechicle category is required.")
    private VehicleCategory category;

    @NotNull(message = "Vechicle model is required.")
    @ManyToOne(optional = false)
    private Model model;

    @NotNull(message = "Vechicle manufacturing year is required.")
//    @PastOrPresent
    @Min(1900)
    private Integer year = LocalDate.now().getYear();

    @NotNull(message = "Vechicle mileage in km is required.")
    @Positive
    private Integer mileage;

    @NotNull(message = "Vechicle engine type is required.")
    private EngineType engine;

    @NotNull(message = "Vechicle transmission type is required.")
    private TransmissionType transmission;

    @Length(min = 0, max = 512)
    private String description;

    @NotNull(message = "Vechicle price is required.")
    @Positive
    private Double price;

//    @NotNull(message = "Vechicle imageUrl is required.")
//    @Length(min = 8, max = 512)
    private String imageUrl;

    @ManyToOne(optional = true)
    @ToString.Exclude
    private User seller;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Transient
//    private Long modelId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long sellerId;


    private Date created = new Date();
    private Date modified = new Date();
}

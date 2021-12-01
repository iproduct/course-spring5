package demos.spring.vehicles.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model implements Comparable<Model>{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NonNull
    @Length(min = 1, max = 40)
    private String name;

    @NonNull
    @NotNull
    private VehicleCategory category;

    @NonNull
    @NotNull
    @Min(1900)
    private Integer startYear;

    @Min(1900)
    private Integer endYear;

    @NonNull
    @ToString.Exclude
    @NotNull
    @ManyToOne
    private Brand brand;

    @NonNull
    @Length(min = 8, max = 512)
    private String imageUrl;

    private Date created = new Date();
    private Date modified = new Date();

    public Model(String name, VehicleCategory category, Integer startYear, Integer endYear, String imageUrl) {
        this.name = name;
        this.category = category;
        this.startYear = startYear;
        this.imageUrl = imageUrl;
        this.endYear = endYear;
    }

    @Override
    public int compareTo(Model o) {
        return name.compareToIgnoreCase(o.getName());
    }
}

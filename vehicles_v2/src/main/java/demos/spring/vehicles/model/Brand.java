package demos.spring.vehicles.model;

import lombok.*;
import org.hibernate.annotations.SortComparator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Brand implements Comparable<Brand>{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @Length(min = 2, max = 40)
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Model> models = new ArrayList<>();

    private Date created = new Date();
    private Date modified = new Date();

    @Override
    public int compareTo(Brand o) {
        return name.compareToIgnoreCase(o.getName());
    }

    public static Brand create(String name, Set<Model> models) {
        Brand brand = new Brand(name);
        models.stream().sorted().forEach(model -> {
            model.setBrand(brand);
            brand.getModels().add(model);
        });
        return brand;
    }

}

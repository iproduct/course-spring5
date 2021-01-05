package course.springdata.advanced.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredients")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private double price;

    @ToString.Exclude
    @ManyToMany(mappedBy = "ingredients")
    private Set<Shampoo> shampoos = new HashSet<>();

}

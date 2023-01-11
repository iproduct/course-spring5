package course.hibernate.spring.entity;

import lombok.*;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "property_holder")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PropertyHolder {
    @Id
    @NonNull
    private Long id;

        // for Hibernate 6 use:
//    @AnyDiscriminator(DiscriminatorType.CHAR)
//    @AnyDiscriminatorValue(discriminator = "S", entity = StringProperty.class)
//    @AnyDiscriminatorValue(discriminator = "I", entity = IntegerProperty.class)
//    @AnyKeyJavaClass(Long.class)
    @Column(name = "property_type")
    @ManyToAny(
            metaDef = "PropertyMetaDef",
            metaColumn = @Column(name="property_type")
    )
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    @JoinTable(name="holder_properties",
            joinColumns = @JoinColumn(name="holder_id"),
            inverseJoinColumns = @JoinColumn(name="property_id")
    )
    private Set<Property> properties = new HashSet<>();
}

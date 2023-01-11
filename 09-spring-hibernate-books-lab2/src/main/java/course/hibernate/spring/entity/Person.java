package course.hibernate.spring.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Person {
    @Id
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    @Column(name="city_name")
    private String cityName;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "city_name", referencedColumnName = "name", insertable = false, updatable = false)
    private City city;
}

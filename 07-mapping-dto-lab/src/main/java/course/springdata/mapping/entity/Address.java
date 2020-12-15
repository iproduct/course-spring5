package course.springdata.mapping.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String country;
    @NonNull
    private String city;
    @NonNull
    private String details;
}

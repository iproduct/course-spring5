package course.springdate.jpaintro.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "plate_numbers")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PlateNumber {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    @NonNull
    @ToString.Exclude
    private Car car;
}

package course.hibernate.spring.entity;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Immutable
public class SystemUserEmbeddedId {
    @EmbeddedId
    @NonNull
    private EmbeddedPK id;
    @ManyToOne
    @MapsId("subsystem")
    Subsystem subsystem;
    @NonNull
    private String name;
}

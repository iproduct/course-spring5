package course.spring.restmvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    @Id
    @SequenceGenerator(name = "categorySeqGen", sequenceName = "categorySeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeqGen")
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String description;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany
//    @JoinColumn(name="author_id", nullable = false, updatable = false, referencedColumnName = "id",
//            foreignKey = @ForeignKey(name="fkPostsUsersId"))
    private Set<Post> posts = new LinkedHashSet<>();

    LocalDateTime created = LocalDateTime.now();
    LocalDateTime modified = LocalDateTime.now();

}

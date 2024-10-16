package course.spring.intro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TAGS")
    private Set<String> tags = Collections.EMPTY_SET;
}

package course.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
@Component
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

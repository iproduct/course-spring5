package course.spring.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    @NotNull
    @Size(min = 5, max=80)
    private String title;
    @NonNull
    @NotBlank(message = "Content must not be empty")
    private String content;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TAGS")
    private Set<String> tags = Collections.EMPTY_SET;
}

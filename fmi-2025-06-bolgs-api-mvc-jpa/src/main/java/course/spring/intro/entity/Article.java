package course.spring.intro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotBlank
    @Size(min = 3, max = 100)
    private String title;
    @NonNull
    private String content;
    @NonNull
    @ManyToOne
    @JoinColumn(name="AUTHOR_ID",  nullable = false)
    private User author;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> tags =  new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Category> categories = Set.of(Category.OTHER);
    private LocalDateTime created =  LocalDateTime.now();
    private LocalDateTime modified =  LocalDateTime.now();
}

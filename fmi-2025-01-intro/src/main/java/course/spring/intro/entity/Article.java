package course.spring.intro.entity;

import jakarta.persistence.*;
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
    Long id;
    @NonNull
    String title;
    @NonNull
    String content;
    @NonNull
    String author;
    @ElementCollection
    Set<String> tags =  new HashSet<>();
    @ElementCollection
    @Enumerated(EnumType.STRING)
    Set<Category> categories = Set.of(Category.OTHER);
    LocalDateTime created =  LocalDateTime.now();
    LocalDateTime modified =  LocalDateTime.now();
}

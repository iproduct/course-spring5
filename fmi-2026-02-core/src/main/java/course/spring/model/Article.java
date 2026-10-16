package course.spring.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static course.spring.model.Category.OTHER;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article {
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String author;
    private Set<Category> categories = Set.of(OTHER);
    private Set<String> tags = new HashSet<>();
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public Article(Long id, String title, String content, String author, Set<Category> categories, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.categories = categories;
        this.tags = tags;
    }
}

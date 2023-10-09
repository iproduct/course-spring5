package course.spring.intro.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    @NonNull
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String author;
    private Set<String> keywords = Set.of();
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public Article(@NonNull Long id, @NonNull String title, @NonNull String content, @NonNull String author, Set<String> keywords) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.keywords = keywords;
    }
}

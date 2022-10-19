package course.spring.core.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String imageUrl;
    @NonNull
    private Set<String> tags;
    @NonNull
    private String author;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public Article(Long id, @NonNull String title, @NonNull String content, @NonNull String imageUrl, @NonNull Set<String> tags, @NonNull String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.author = author;
    }
}

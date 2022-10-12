package course.spring.demo.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @NonNull
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private Set<String> tags = Set.of();

    public Post(@NonNull Long id, @NonNull String title, @NonNull String content, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

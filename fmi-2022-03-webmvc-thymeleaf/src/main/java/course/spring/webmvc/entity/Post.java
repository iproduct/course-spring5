package course.spring.webmvc.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;

    @NonNull
    private String imageUrl;
    @ElementCollection
    private Set<String> tags = Set.of();

    public Post(@NonNull String title, @NonNull String content, @NonNull String imageUrl, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

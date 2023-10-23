package course.spring.intro.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NonNull
    private String title;
    @NonNull
    @Column(length = 1024)
    private String content;
    @NonNull
    private String author;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> tags = Set.of();
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public Article(@NonNull String title, @NonNull String content, @NonNull String author, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags = tags;
    }
}

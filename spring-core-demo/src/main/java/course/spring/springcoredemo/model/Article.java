package course.spring.springcoredemo.model;


import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private String author;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modified = LocalDateTime.now();
}

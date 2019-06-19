package course.spring.springcoredemo.model;


import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    private String id;
    private String title;
    private String content;
    private Author author;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modified = LocalDateTime.now();

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

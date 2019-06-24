package course.spring.webfluxdemo.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private String author;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime updated = LocalDateTime.now();
}

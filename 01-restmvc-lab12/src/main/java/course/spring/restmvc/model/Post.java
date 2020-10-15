package course.spring.restmvc.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class Post {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String author;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

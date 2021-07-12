package course.spring.blogs.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private Long authorId;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

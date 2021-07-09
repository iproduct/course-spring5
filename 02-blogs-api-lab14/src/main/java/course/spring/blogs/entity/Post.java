package course.spring.blogs.entity;

import lombok.*;

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
    private Long authorId;
    private Date created = new Date();
    private Date modified = new Date();
}

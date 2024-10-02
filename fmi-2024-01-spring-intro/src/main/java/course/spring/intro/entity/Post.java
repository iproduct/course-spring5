package course.spring.intro.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class Post {
    @NonNull
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
}

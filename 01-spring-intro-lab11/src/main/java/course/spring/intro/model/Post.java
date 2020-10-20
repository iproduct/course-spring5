package course.spring.intro.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String author;
    private String umageUrl;
    private List<String> keywords = new ArrayList<>();
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

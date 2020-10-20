package course.spring.intro.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Post {
    private String id;
    private String title;
    private String content;
    private String author;
    private String umageUrl;
    private List<String> keywords = new ArrayList<>();
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

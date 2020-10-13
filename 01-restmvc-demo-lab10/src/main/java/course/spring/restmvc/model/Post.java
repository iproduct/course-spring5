package course.spring.restmvc.model;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Post {
    private Long id;
    @NonNull
    @NotNull
    private String title;
    @NonNull
    @NotNull
    private String content;
    @NonNull
    @NotNull
    private String author;
    private List<String> keywords;
    @URL
    private String imageUrl;
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();
}

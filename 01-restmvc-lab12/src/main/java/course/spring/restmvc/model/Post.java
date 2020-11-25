package course.spring.restmvc.model;

import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection="posts")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Pattern(regexp = "[A-Za-z0-9]{24}", message = "Invalid post ID") // Mongodb ObjectID
    private String id;
    @NonNull
    @NotNull
    @Size(min=2, max=80)
    private String title;
    @NonNull
    @NotNull
    @Size(min=2, max=2048)
    private String content;
    @NonNull
    @URL
    private String imageUrl;
    @Pattern(regexp = "[A-Za-z0-9]{24}", message = "Invalid author ID")
    private String authorId;
    private List<@Pattern(regexp = "^[\\w\\s-]+$", message = "Invalid keyword - should contain only letters and digits") String> keywords = new ArrayList<>();
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    public Post(@NonNull String title, @NonNull String content, @NonNull String imageUrl, List<String> keywords) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.keywords = keywords;
    }
}

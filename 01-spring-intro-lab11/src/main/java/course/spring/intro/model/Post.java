package course.spring.intro.model;

import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Pattern(regexp = "^[A-Fa-f0-9]{24}$")
    private String id;
    @NonNull
    @NotNull
    @Size(min=2, max=80)
    private String title;
    @NonNull
    @NotNull
    @Size(min=2, max=2048)
    private String content;
    @NotNull
    @Pattern(regexp = "^[A-Fa-f0-9]{24}$")
    private String authorId;
    @URL
    private String imageUrl;
    private List<String> keywords = new ArrayList<>();
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

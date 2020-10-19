package course.spring.restmvc.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    @NonNull
    @NotNull
    @Size(min=2, max = 50)
    private String title;

    @NonNull
    @NotNull
    @Size(min=20, max = 2048)
    private String content;

    @NonNull
    private UserDto author;

    private List<@Size(min=2, max=30) String> keywords;

    @URL
    private String imageUrl;

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    public PostDto(@NonNull @NotNull @Size(min = 2, max = 50) String title, @NonNull @NotNull @Size(min = 20, max = 2048) String content, @NonNull @NotNull UserDto author, List<@Size(min = 2, max = 15) String> keywords, @URL String imageUrl) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.keywords = keywords;
        this.imageUrl = imageUrl;
    }
}

package course.spring.webmvc.entity;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Size(min=2, max=60)
    @NotBlank
    private String title;
    @NonNull
    @NotBlank
    @Size(min=10, max=2048)
    private String content;

    @NonNull
//    @URL
//    @NotBlank
    private String imageUrl;
    @ElementCollection
    private Set<@Pattern(regexp = "\\w+") String> tags = Set.of();

    public Post(@NonNull String title, @NonNull String content, @NonNull String imageUrl, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

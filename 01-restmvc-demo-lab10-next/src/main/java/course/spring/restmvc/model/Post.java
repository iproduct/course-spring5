package course.spring.restmvc.model;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue
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
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]+\\s+[A-Z][a-z]+.*", message="First and last names should be provided.")
    private String author;
    @ElementCollection
    private List<@Size(min=2, max=15) String> keywords;
    @URL
    private String imageUrl;
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();
}

package course.ws.blogs.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="articles")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotBlank
    @Size(min=3, max=60)
    private String title;
    @NonNull
    @NotEmpty
    @Size(min=15, max=2048)
    private String content;
    @NonNull
    @NotNull
    @Pattern(regexp = "^([A-Za-z]+).*\\s([A-Za-z]+)$", message = "Invalid author name - at least two names should be given")
    private String author;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="article_keywords")
    @NonNull
    private Set<@Pattern(regexp = "^[\\w\\s\\+-]+$") String> keywords;
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();
}

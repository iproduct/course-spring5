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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="article_keywords")
    @NonNull
    private Set<@Pattern(regexp = "^[\\w\\s\\+-]+$") String> keywords;
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();
}

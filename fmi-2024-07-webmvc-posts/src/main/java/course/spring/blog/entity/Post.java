package course.spring.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    @NotNull
    @Size(min = 5, max=80)
    private String title;
    @NonNull
    @NotBlank(message = "Content must not be empty")
    private String content;
    @NotNull
    private String imageUrl;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID", nullable = true)
    private User author;

    @DateTimeFormat(pattern="dd.MM.YYYY")
    private LocalDateTime created = LocalDateTime.now();
    @DateTimeFormat(pattern="dd.MM.YYYY")
    private LocalDateTime modified = LocalDateTime.now();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TAGS")
    private Set<String> tags = Collections.EMPTY_SET;

    public Post(@NonNull String title, @NonNull String content, String imageUrl, User author, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.author = author;
        this.tags = tags;
    }
}

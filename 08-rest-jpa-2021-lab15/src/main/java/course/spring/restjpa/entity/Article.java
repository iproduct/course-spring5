package course.spring.restjpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Length(min=3, max=80)
    private String title;

    @NonNull
    @Length(min=3, max=2048)
    private String content;

    @ManyToOne(optional = false)
    private User author;

    @Length(min=3, max=256)
    private String pictureUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags = new ArrayList();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime modified = LocalDateTime.now();

    public Article(@NonNull String title, @NonNull String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags = tags;
    }
}

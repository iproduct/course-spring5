package course.spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article implements Identifiable<Long>{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    @Size(min= 2, max = 60)
    @Column(length = 60)
    private String title;
    @Column(length = 2048)
    @Size(min= 10, max = 2048)
    private String content;
    private String imageUrl;
    @ManyToOne
    private User author;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> keywords;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created = LocalDateTime.now();
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modified = LocalDateTime.now();

    public Article(String title, String content, String imageUrl, User author, Set<String> keywords) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.author = author;
        this.keywords = keywords;
    }
}

package course.spring.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article implements Identifiable<Long>{
    @EqualsAndHashCode.Include
    private Long id;
    @Size(min= 2, max = 60)
    private String title;
    @Size(min= 10, max = 2048)
    private String content;
    private String imageUrl;
    private User author;
    private Set<String> keywords;

    public Article(String title, String content, String imageUrl, User author, Set<String> keywords) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.author = author;
        this.keywords = keywords;
    }
}

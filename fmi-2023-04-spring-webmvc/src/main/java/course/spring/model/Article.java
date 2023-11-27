package course.spring.model;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article implements Identifiable<Long>{
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
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

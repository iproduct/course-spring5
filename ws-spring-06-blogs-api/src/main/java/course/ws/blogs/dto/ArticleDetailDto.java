package course.ws.blogs.dto;

import course.ws.blogs.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArticleDetailDto {
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    @Size(min=3, max=50)
    private String title;
    @NotNull
    @Size(min=10, max=2048)
    private String content;
    @NotNull
    private String author;

    private Set<String> keywords = Set.of();

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public ArticleDetailDto(String title, String content, String author, Set<String> keywords) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.keywords = keywords;
    }
}

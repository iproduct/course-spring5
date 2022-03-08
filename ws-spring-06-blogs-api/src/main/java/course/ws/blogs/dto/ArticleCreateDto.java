package course.ws.blogs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArticleCreateDto {
    @NotNull
    @Size(min=3, max=50)
    private String title;
    @NotNull
    @Size(min=10, max=2048)
    private String content;

    private Set<@Pattern(regexp = "^[\\w\\s\\-]+$") String> keywords = Set.of();

    public ArticleCreateDto(String title, String content, Set<String> keywords) {
        this.title = title;
        this.content = content;
        this.keywords = keywords;
    }
}

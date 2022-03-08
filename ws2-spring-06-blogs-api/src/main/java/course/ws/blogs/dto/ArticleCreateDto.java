package course.ws.blogs.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateDto {
    @NotBlank
    @Size(min=3, max=60)
    private String title;
    @NotEmpty
    @Size(min=15, max=2048)
    private String content;
    private Set<@Pattern(regexp = "^[\\w\\s\\+-]+$") String> keywords;
}

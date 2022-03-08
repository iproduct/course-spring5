package course.ws.blogs.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArticleUpdateDto {
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
    private Set<@Pattern(regexp = "^[\\w\\s\\+-]+$") String> keywords;
}

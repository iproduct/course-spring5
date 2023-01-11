package course.spring.blogs.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ArticleDetailDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private Set<String> tags;
    private String author;
}


package course.spring.blogs.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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


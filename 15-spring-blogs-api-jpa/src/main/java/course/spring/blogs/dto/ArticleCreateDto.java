package course.spring.blogs.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record ArticleCreateDto(
        @NotNull @Size(min = 3, max = 40) @NonNull String title,
        @NotNull @Size(min = 15, max = 1024) @NonNull String content,
        @NotNull @URL @NonNull String imageUrl,
        @NonNull Set<@Pattern(regexp = "^[\\w\\s]+$") String> tags) {
}


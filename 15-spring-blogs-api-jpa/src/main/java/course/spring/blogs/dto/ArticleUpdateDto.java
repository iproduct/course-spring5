package course.spring.blogs.dto;

import lombok.NonNull;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.*;
import java.util.Set;

public record ArticleUpdateDto(
        @NotNull @Positive @NonNull Long id,
        @NotNull @Size(min = 3, max = 40) @NonNull String title,
        @NotNull @Size(min = 15, max = 1024) @NonNull String content,
        @NotNull @URL @NonNull String imageUrl,
        @NonNull Set<@Pattern(regexp = "^[\\w\\s]+$") String> tags) {
}


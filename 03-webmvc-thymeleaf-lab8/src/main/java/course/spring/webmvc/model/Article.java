package course.spring.webmvc.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Document("articles")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    private String id;
    @NonNull
    @NotNull
    @Size(min= 2, max = 60)
    private String title;
    @NonNull
    @Size(min= 10, max = 2048)
    private String content;
    @NonNull
    @Pattern(regexp = "[0-9a-d]{24}")
    private String authorId;
    @NonNull
    private String authorName;
    private String pictureUrl;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created = LocalDateTime.now();
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modified = LocalDateTime.now();
}

package course.spring.restmvc.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @Size(min= 5)
    private String author;
    private String pictureUrl;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

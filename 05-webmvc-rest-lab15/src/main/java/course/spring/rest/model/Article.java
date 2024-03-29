package course.spring.rest.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private LocalDateTime created = LocalDateTime.now();
}

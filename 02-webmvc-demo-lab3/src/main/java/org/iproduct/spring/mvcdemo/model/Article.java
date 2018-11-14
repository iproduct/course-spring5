package org.iproduct.spring.mvcdemo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;

@Document(collection = "articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Article {
    @Id
    @NonNull
    private String id;
    @NonNull
    @NotNull
    @Size(min = 3, max = 80)
    private String title;
    @NonNull
    @NotNull
    private String content;
    @NonNull
    @Size(min = 3, max = 30)
    @Pattern(regexp = ".+(\\s.+){1,3}")
    private String author;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

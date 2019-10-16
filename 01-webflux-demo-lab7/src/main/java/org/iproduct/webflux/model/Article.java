package org.iproduct.webflux.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String title;
    @NonNull
    private String content;
    private String author;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}

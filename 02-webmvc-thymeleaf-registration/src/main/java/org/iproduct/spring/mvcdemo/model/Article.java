package org.iproduct.spring.mvcdemo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String author;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created = LocalDateTime.now();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modified = LocalDateTime.now();
}

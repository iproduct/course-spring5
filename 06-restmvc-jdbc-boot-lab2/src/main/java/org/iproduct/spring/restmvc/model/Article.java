package org.iproduct.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "articles")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    @Id
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();
}

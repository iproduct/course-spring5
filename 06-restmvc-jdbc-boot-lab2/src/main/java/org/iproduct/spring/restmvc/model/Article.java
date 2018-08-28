package org.iproduct.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
//    @Id
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();
}

package org.iproduct.spring.webmvc.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
}

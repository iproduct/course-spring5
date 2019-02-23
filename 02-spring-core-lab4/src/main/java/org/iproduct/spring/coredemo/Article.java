package org.iproduct.spring.coredemo;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Article {
    @NonNull
    private String title;

    @NonNull
    private String content;

    private LocalDateTime createdDate = LocalDateTime.now();
}

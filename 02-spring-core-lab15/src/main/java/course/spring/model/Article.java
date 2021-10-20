package course.spring.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private String author;
    private LocalDateTime created = LocalDateTime.now();
}

package course.spring.coredemo.model;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
}

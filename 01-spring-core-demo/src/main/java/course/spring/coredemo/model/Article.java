package course.spring.coredemo.model;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    private static long nextId = 0L;
    private Long id = ++nextId;
    @NonNull
    private String title;
    @NonNull
    private String content;
}

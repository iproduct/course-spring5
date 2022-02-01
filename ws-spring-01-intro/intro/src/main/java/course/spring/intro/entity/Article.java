package course.spring.intro.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    @NonNull
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String auhor;
    @NonNull
    private Set<String> keywords = Set.of();
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

}

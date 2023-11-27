package course.spring.model;

import lombok.*;

import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArticleCreateDTO implements Identifiable<Long>{
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String authorUsername;
    @NonNull
    private Set<String> keywords;

}

package course.spring.model;

import course.spring.dao.Identifiable;
import lombok.*;

import java.util.Collections;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post implements Identifiable<Long> {
    @NonNull
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private Set<String> tags = Collections.EMPTY_SET;

    public Post(@NonNull String title, @NonNull String content, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Post(@NonNull String title, @NonNull String content) {
        this.title = title;
        this.content = content;
    }
}

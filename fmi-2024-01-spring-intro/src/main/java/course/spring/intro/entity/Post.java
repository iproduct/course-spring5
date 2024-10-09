package course.spring.intro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Collections;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Post {
    @NonNull
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private Set<String> tags = Collections.EMPTY_SET;
}

package course.ws.model;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article implements Identifiable<Long>{
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String author;
    @NonNull
    private Set<String> keywords;
}

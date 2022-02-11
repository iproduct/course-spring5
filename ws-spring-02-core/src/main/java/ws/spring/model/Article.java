package ws.spring.model;

import lombok.*;
import ws.spring.dao.Identifiable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article implements Identifiable<Long> {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private User author;

}

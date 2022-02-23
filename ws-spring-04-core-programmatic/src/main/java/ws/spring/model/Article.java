package ws.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;

}

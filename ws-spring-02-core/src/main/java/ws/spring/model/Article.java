package ws.spring.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Article {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;

}

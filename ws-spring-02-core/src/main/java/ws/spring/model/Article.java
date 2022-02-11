package ws.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ws.spring.dao.Identifiable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Identifiable<Long> {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;

}

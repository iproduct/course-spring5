package course.spring.restjpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
//    @Id
    private long id;

    @NonNull
    @Length(min=3, max=80)
    private String title;

    @NonNull
    @Length(min=3, max=2048)
    private String content;

    private long authorId;

    @Length(min=3, max=256)
    private String pictureUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime updated = LocalDateTime.now();
}

package patchdemo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Document(collection = "articles")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    @Id
    private String id;
    @NonNull @NotNull
    @Size(min=3, max=60)
    private String title;
    @NonNull @NotBlank
    private String content;
    private String pictureUrl;
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modified = LocalDateTime.now();

    public Article(String id, @NonNull @NotNull @Size(min = 3, max = 60) String title, @NonNull @NotBlank String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}

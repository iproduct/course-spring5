package course.spring.coredemo.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {

    private static AtomicInteger nextId = new AtomicInteger(0);
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private Author author;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime updated = LocalDateTime.now();

    @Value("${articles.titles}")
    @ToString.Exclude
    private String[] titles;

    @Value("${articles.contents}")
    @ToString.Exclude
    private String[] contents;

    private void init() {
        int index = nextId.getAndIncrement();
        title=titles[index % titles.length];
        content = contents[index % contents.length];
    }
}

package course.spring.springcoredemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope("prototype")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    private static final AtomicInteger nextId = new AtomicInteger(0);
    private String id;
    private String title;
    private String content;
    private Author author;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modified = LocalDateTime.now();

    @Value("${articles.titles}")
    private String[] titles;

    @Value("${articles.contents}")
    private String[] contents;

    @PostConstruct
    private void init() {
        int index = nextId.getAndIncrement();
        title = titles[index];
        content = contents[index];
    }

    @Autowired
    public void setAuthor(Author author) {
        this.author = author;
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

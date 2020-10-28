package course.spring.coredemo.model;


import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("post")
@Scope("prototype")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Article {
    private static int nextId = 0;
//    @Value("${titles}")
//    private String titlesStr;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        String titlesStr = environment.getProperty("titles");
        id = ++nextId;
        String[] titles = titlesStr.split(", ");
        title = titles[id % titles.length];
        content = title + " content ...";
    }

    private int id;
    @NonNull
    private String title;
    @NonNull
    private String content;
}

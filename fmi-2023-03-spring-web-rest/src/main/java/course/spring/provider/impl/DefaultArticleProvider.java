package course.spring.provider.impl;

import course.spring.model.Article;
import course.spring.model.User;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.Default;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static course.spring.model.Role.*;

@Service
@Qualifier("defaultProvider")
@Default
public class DefaultArticleProvider implements ArticleProvider {
    @Override
    public List<Article> getArticles() {
        var user = new User(1L, "Default Admin", "default", "default123", Set.of(READER, AUTHOR, ADMIN));
        return List.of(
                new Article(1L, "New in Spring", "Reactive programming with WebFlux is new ...",user, Set.of("new", "spring", "reactive", "webflux")),
                new Article(2L, "Spring Data JPA", "Spring Data makes data management much simpler ...",user, Set.of("spring data", "jpa", "db")),
                new Article(3L, "Spring Security", "Spring provides a comprehensive security implementations for your web apps and services ...",user, Set.of("spring", "security", "web"))
        );
    }
}

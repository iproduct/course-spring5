package course.spring.provider.impl;

import course.spring.model.Article;
import course.spring.model.User;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.Default;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static course.spring.model.Role.*;

@Service
@Qualifier("defaultProvider")
@Default
public class DefaultArticleProvider implements ArticleProvider {
    @Override
    public Article createArticle(Article article) {
        throw new RuntimeException("Method not implemented yet.");
    }

    @Override
    public int getArticlesCount() {
        return 3;
    }

    @Override
    public List<Article> getArticles() {
        var user = new User("Default", "Admin", "default", "default123", Set.of(READER, AUTHOR, ADMIN));
        user.setId(1L);
        return List.of(
                new Article("New in Spring", "","Reactive programming with WebFlux is new ...",user, Set.of("new", "spring", "reactive", "webflux")),
                new Article("Spring Data JPA", "", "Spring Data makes data management much simpler ...",user, Set.of("spring data", "jpa", "db")),
                new Article("Spring Security", "","Spring provides a comprehensive security implementations for your web apps and services ...",user, Set.of("spring", "security", "web"))
        );
    }

    @Override
    public Article getArticleById(long id) {
        throw new RuntimeException("Method not implemented yet.");
    }
}

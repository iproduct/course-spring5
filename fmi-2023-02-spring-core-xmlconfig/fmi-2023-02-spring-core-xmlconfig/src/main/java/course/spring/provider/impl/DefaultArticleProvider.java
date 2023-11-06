package course.spring.provider.impl;

import course.spring.model.Article;
import course.spring.provider.ArticleProvider;

import java.util.List;
import java.util.Set;

public class DefaultArticleProvider implements ArticleProvider {
    @Override
    public List<Article> getArticles() {
        return List.of(
                new Article(1L, "New in Spring", "Reactive programming with WebFlux is new ...","Trayan Iliev", Set.of("new", "spring", "reactive", "webflux")),
                new Article(2L, "Spring Data JPA", "Spring Data makes data management much simpler ...","Trayan Iliev", Set.of("spring data", "jpa", "db")),
                new Article(3L, "Spring Security", "Spring provides a comprehensive security implementations for your web apps and services ...","Trayan Iliev", Set.of("spring", "security", "web"))
        );
    }
}

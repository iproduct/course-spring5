package course.ws.service.impl;

import course.ws.model.Article;
import course.ws.qualifiers.Mock;
import course.ws.service.ArticleProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

//@Service
//@Mock
public class MockArticleProvider implements ArticleProvider {
    public static final List<Article> SAMPLE_ARTICLES = List.of(
            new Article("Intro to Spring", "Spring MVC is easy ...",
                    "Trayan Iliev", Set.of("spring", "mvc", "boot", "intro")),
            new Article("Hibernate Performance", "Hibernate provides powerful ORM implementation ...",
                    "Georgi Petrov", Set.of("hibernate", "performance")),
            new Article( "Spring Boot is Easy", "Spring Boot makes bootstrapping new Spring projects easy ...",
                    "Trayan Iliev", Set.of("spring", "boot", "intro"))
    );

    @Override
    public List<Article> getArticles() {
        return SAMPLE_ARTICLES;
    }
}

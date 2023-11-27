package course.spring.init;


import course.spring.model.Article;
import course.spring.model.User;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.RepoBacked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

import static course.spring.model.Role.*;
import static course.spring.provider.impl.RepoArticleProvider.DEFAULT_USER;

@Component
public class DataInitializer implements CommandLineRunner {
    private ArticleProvider articleProvider;

    public DataInitializer(@RepoBacked ArticleProvider articleProvider) {
        this.articleProvider = articleProvider;
    }

    @Override
    public void run(String... args) throws Exception {
        if (articleProvider.getArticlesCount() == 0) {
            articleProvider.createArticle(new Article("Spring Intro", "Spring is developer friendly web service platform", DEFAULT_USER, Set.of("spring", "intro")));
            articleProvider.createArticle(new Article("Spring Data JPA", "Spring Data JPA allows easy RDBMS persis1tence.", DEFAULT_USER, Set.of("spring data", "jpa")));
        }
    }
}

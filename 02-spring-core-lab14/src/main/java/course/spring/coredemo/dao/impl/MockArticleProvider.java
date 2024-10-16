package course.spring.coredemo.dao.impl;

import course.spring.coredemo.dao.ArticleProvider;
import course.spring.coredemo.model.Article;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope("singleton")
@Order(2)
public class MockArticleProvider implements ArticleProvider {
    @Override
    public List<Article> getArticles() {
        return List.of(
            new Article("Dependency Injection", "Should I use DI or service lookup pattern? ..."),
            new Article("Apring AOP", "AOP is easy ..."),
            new Article("Spring Beans and Wiring", "There are several ways to configure Spring beans ...")
        );
    }
}

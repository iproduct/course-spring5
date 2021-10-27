package course.spring.service.impl;

import course.spring.dao.ArticleRepository;
import course.spring.model.Article;
import course.spring.service.ArticleProvider;

import java.util.Collection;
import java.util.List;

public class MockArticleProvider implements ArticleProvider {
    public static final List<Article> MOCK_ARTICLES =List.of(
        new Article("New in Spring", "Web Flux is here ..."),
        new Article("DI in Action", "Should I use DI or Service Locator? ..."),
        new Article("Bean Scopes", "Singleton, Prototype, ...")
    );

    private ArticleRepository articleRepo;

    public void init() {
        MOCK_ARTICLES.forEach(articleRepo::create);
    }

    @Override
    public Collection<Article> getArticles() {
        return articleRepo.findAll();
    }
}

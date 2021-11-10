package course.spring.service.impl;

import course.spring.dao.ArticleRepository;
import course.spring.dao.impl.ArticleRepositoryImpl;
import course.spring.model.Article;
import course.spring.qualifiers.Mock;
import course.spring.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

//@Mock
//@Order(2)
//@Service("mockProvider")
public class MockArticleProvider implements ArticleProvider {
    public static final List<Article> MOCK_ARTICLES =List.of(
        new Article("New in Spring", "Web Flux is here ..."),
        new Article("DI in Action", "Should I use DI or Service Locator? ..."),
        new Article("Bean Scopes", "Singleton, Prototype, ...")
    );

    @Autowired
    private ArticleRepository articleRepo;

    @PostConstruct
    public void init() {
        MOCK_ARTICLES.forEach(articleRepo::create);
    }

    @Override
    public Collection<Article> getArticles() {
        return articleRepo.findAll();
    }
}

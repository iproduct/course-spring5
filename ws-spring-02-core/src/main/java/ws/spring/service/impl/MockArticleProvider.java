package ws.spring.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ws.spring.model.Article;
import ws.spring.qualifier.MockProvider;
import ws.spring.service.ArticleProvider;

import java.util.Collection;
import java.util.List;

@Service
@MockProvider
//@Order(2)
public class MockArticleProvider implements ArticleProvider {
    public static final List<Article> MOCK_ARTICLES = List.of(
        new Article(1L, "New in Spring", "Spring 5 is great beacuse ...", null),
        new Article(2L, "Spring DI", "Should I use DI or lookup ...", null),
        new Article(3L, "Spring Autowiring", "There are several ways to configure Spring beans.", null)
    );

    @Override
    public Collection<Article> getArticles() {
        return MOCK_ARTICLES;
    }
}

package course.spring.coredemo.provider;

import course.spring.coredemo.model.Article;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MockArticleProvider implements ArticleProvider {
    private AtomicLong nextId = new AtomicLong(0L);
    private Map<Long, Article> articles = new ConcurrentHashMap<>();

    public MockArticleProvider() {
        Article[] mockArticles = {
                new Article("New Spring 5", "WebFlux is here ..."),
                new Article("DI Basics", "There are many ways to DI in Spring ..."),
                new Article("Reactive Spring", "WebFlux is based on project Ractor  ...")
        };
        Arrays.stream(mockArticles).forEach(this::addArticle);
    }

    @Override
    public List<Article> getAticles() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Article addArticle(Article article) {
        article.setId(nextId.incrementAndGet());
        return articles.put(article.getId(), article);
    }

    public static ArticleProvider createProvider() {
        MockArticleProvider provider = new MockArticleProvider();
        provider.addArticle(new Article("Created by factory method", "Created by factory method content ..."));
        return provider;
    }
}

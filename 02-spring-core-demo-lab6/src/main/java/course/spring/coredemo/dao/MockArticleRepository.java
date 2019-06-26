package course.spring.coredemo.dao;

import course.spring.coredemo.model.Article;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//@Repository("repo")
public class MockArticleRepository implements ArticleRepository {
    private static AtomicLong nextId = new AtomicLong(0);
    private Map<String, Article> articles = new ConcurrentHashMap<>();

    public MockArticleRepository() {
        Arrays.asList(
                new Article("Spring DI",
                        "DI is needed for component-based engineering ..."),
                new Article("WebFlux for Performant IO",
                        "WebFlux is the new asyncronous web API in Spring 5 ..."),
                new Article("Reactor High Performance",
                        "Reactor project allows to achieve tens of millions ops per sec ...")
        ).stream().forEach(a -> insert(a));
    }

    @Override
    public Collection<Article> findAll() {
        return articles.values();
    }

    @Override
    public Optional<Article> findById(String articleId) {
        return Optional.ofNullable(articles.get(articleId));
    }

    @Override
    public Article insert(Article article) {
        long id = nextId.incrementAndGet();
        article.setId(String.format("%024d", id));
        articles.put(article.getId(), article);
        return article;
    }

    @Override
    public Article save(Article article) {
         articles.put(article.getId(), article);
         return article;
    }

    @Override
    public Article deleteById(String articleId) {
        return articles.remove(articleId);
    }
}

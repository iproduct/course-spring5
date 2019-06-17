package course.spring.webfluxdemo.dao;

import course.spring.webfluxdemo.model.Article;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticlesRepositoryImpl implements ArticlesRepository {
    private Map<String, Article> articles = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public ArticlesRepositoryImpl() {
        Arrays.asList(
            new Article("111111111111111111111111", "New in Spring", "WebFlux is here ..."),
            new Article("222222222222222222222222", "Dependency Injection", "DI is easy ..."),
            new Article("333333333333333333333333", "What is REST?", "REST requires HATEOAS ...")
        ).stream().forEach(a -> {
            articles.put(a.getId(), a);
        });
    }
    @Override
    public Collection<Article> findAll() {
        return articles.values();
    }
    @Override
    public Article create(Article article) {
        long id = nextId.getAndIncrement();
        String idStr = String.format("%024d", id);
        article.setId(idStr);
        articles.put(article.getId(), article);
        return article;
    }

    @Override
    public Article update(Article article) {
        Article old = articles.get(article.getId());
        old.setTitle(article.getTitle());
        old.setContent(article.getContent());
        old.setModified(LocalDateTime.now());
        return old;
    }

    @Override
    public Article delete(String articleId) {
        return articles.remove(articleId);
    }
}

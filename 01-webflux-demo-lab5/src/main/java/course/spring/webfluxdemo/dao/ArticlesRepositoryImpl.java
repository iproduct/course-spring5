package course.spring.webfluxdemo.dao;

import course.spring.webfluxdemo.model.Article;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticlesRepositoryImpl implements ArticlesRepository {
    private List<Article> articles = new CopyOnWriteArrayList<>();
    private AtomicLong nextId = new AtomicLong(1);

    public ArticlesRepositoryImpl() {
        articles = new CopyOnWriteArrayList<>(Arrays.asList(
                new Article("111111111111111111111111", "New in Spring", "WebFlux is here ..."),
                new Article("222222222222222222222222", "Dependency Injection", "DI is easy ..."),
                new Article("333333333333333333333333", "What is REST?", "REST requires HATEOAS ...")
        ));
    }
    @Override
    public List<Article> findAll() {
        return articles;
    }
    @Override
    public Article create(Article article) {
        long id = nextId.getAndIncrement();
        String idStr = String.format("%024d", id);
        article.setId(idStr);
        articles.add(article);
        return article;
    }
}

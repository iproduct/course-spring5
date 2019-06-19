package course.spring.springcoredemo.dao;

import course.spring.springcoredemo.exception.NonexistingEntityException;
import course.spring.springcoredemo.model.Article;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ArticlesRepositoryImpl implements ArticlesRepository, ApplicationContextAware {
    private Map<String, Article> articles = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);
    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public void init() {
        Arrays.asList(
                ctx.getBean("article", Article.class),
                ctx.getBean("article", Article.class),
                ctx.getBean("article", Article.class)
//    new Article("New in Spring", "WebFlux is here ..."),
//            new Article("Dependency Injection", "DI is easy ..."),
//            new Article("What is REST?", "REST requires HATEOAS ...")
        ).stream().forEach(a -> {
            create(a);
        });
    }

    public ArticlesRepositoryImpl() {}
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
    public Article update(Article article) throws NonexistingEntityException {
        Article old = articles.get(article.getId());
        if(old == null)
            throw new NonexistingEntityException(
                    String.format("Article with ID:%s does not exist.", article.getId()));
        old.setTitle(article.getTitle());
        old.setContent(article.getContent());
        old.setModified(LocalDateTime.now());
        return old;
    }

    @Override
    public Article delete(String articleId) throws NonexistingEntityException{
        Article result = articles.remove(articleId);
        if(result == null)
            throw new NonexistingEntityException(
                    String.format("Article with ID:%s does not exist.", articleId));
        return result;
    }


}

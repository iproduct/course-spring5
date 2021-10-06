package course.spring.intro.dao;

import course.spring.intro.model.Article;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    private Map<Long, Article> articles = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong();

    @Override
    public Collection<Article> findAll() {
        return articles.values();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public Article create(Article article) {
        article.setId(nextId.incrementAndGet());
        articles.put(article.getId(), article);
        return article;
    }
}

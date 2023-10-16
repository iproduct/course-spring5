package course.spring.intro.dao;

import course.spring.intro.model.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticleRepositoryInMemory {
    AtomicLong nextId = new AtomicLong();
    private ConcurrentHashMap<Long, Article> articles = new ConcurrentHashMap<>();

    public ArticleRepositoryInMemory() {
        this(List.of(new Article(1L, "Spring Intro", "Spring is developer friendly web service platform", "T. Iliev")));
    }

    public ArticleRepositoryInMemory(List<Article> initialArticles) {
        initialArticles.forEach(art -> articles.put(art.getId(), art));
    }

    public List<Article> findAll(){
        return new ArrayList<>(articles.values());
    }
    public Optional<Article> findById(Long id){
        return Optional.ofNullable(articles.get(id));
    }

    public Article create(Article article) {
        article.setId(nextId.incrementAndGet());
        articles.put(article.getId(), article);
        return article;
    }

}

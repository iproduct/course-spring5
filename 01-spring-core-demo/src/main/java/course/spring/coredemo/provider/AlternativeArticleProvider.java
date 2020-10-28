package course.spring.coredemo.provider;

import course.spring.coredemo.model.Article;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Repository("provider")
public class AlternativeArticleProvider implements ArticleProvider, ApplicationContextAware {
    private AtomicInteger nextId = new AtomicInteger(0);
    private Map<Integer, Article> articles = new ConcurrentHashMap<>();
    private ApplicationContext ctx;

    public AlternativeArticleProvider() {
    }

    @PostConstruct
    public void init() {
        IntStream.range(0,5).mapToObj(n -> {
            return ctx.getBean("post", Article.class);
        }).forEach(this::addArticle);
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
        AlternativeArticleProvider provider = new AlternativeArticleProvider();
        provider.addArticle(new Article("Created by factory method", "Created by factory method content ..."));
        return provider;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}

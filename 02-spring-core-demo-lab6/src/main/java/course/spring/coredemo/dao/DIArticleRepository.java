package course.spring.coredemo.dao;

import course.spring.coredemo.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class DIArticleRepository implements
        ArticleRepository, InitializingBean, ApplicationContextAware {
    private List<Article> articles = new CopyOnWriteArrayList<>();
    private ApplicationContext ctx;

    @Value("${articles.number}")
    private int numArticles;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Number of articles: {}", numArticles);
        for(int i = 0; i < numArticles; i++) {
            articles.add(ctx.getBean(Article.class));
        }
    }

    @Override
    public Collection<Article> findAll() {
        return articles;
    }

    @Override
    public Optional<Article> findById(String articleId) {
        return Optional.empty();
    }

    @Override
    public Article insert(Article article) {
        return null;
    }

    @Override
    public Article save(Article article) {
        return null;
    }

    @Override
    public Article deleteById(String articleId) {
        return null;
    }

 }

package course.spring.coredemo.dao.impl;

import course.spring.coredemo.dao.ArticleProvider;
import course.spring.coredemo.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Repository
@PropertySource("classpath:articles.properties")
@Order(1)
@Slf4j
public class AlternativeArticleProvider implements ArticleProvider, ApplicationContextAware {
//    @Value("${articles.titles}") // SpEL expression
    private String[] articleTitles;
    private List<Article> articles;
    private ApplicationContext ctx;

    public AlternativeArticleProvider() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @PostConstruct
    public void init() throws Exception {
        articleTitles = ctx.getEnvironment().getProperty("articles.titles").split("\\s*,\\s*");
        articles = Arrays.stream(articleTitles).map(title -> new Article(title, title + " content ..."))
                .collect(Collectors.toList());
        log.info("Init: Created " + articles.size() + " articles.");
    }

    @PreDestroy
    public void cleanup() throws Exception {
        log.info("Destroy: Destroying " + articles.size() + " articles.");
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }


}

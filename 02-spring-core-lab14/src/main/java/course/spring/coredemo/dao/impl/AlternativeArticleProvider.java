package course.spring.coredemo.dao.impl;

import course.spring.coredemo.dao.ArticleProvider;
import course.spring.coredemo.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Repository
@PropertySource("classpath:articles.properties")
@Slf4j
public class AlternativeArticleProvider implements ArticleProvider, InitializingBean, DisposableBean {
    @Value("${articles.titles}") // SpEL expression
    private String[] articleTitles;
    private List<Article> articles;

    public AlternativeArticleProvider() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        articles = Arrays.stream(articleTitles).map(title -> new Article(title, title + " content ..."))
                .collect(Collectors.toList());
        log.info("Init: Created " + articles.size() + " articles.");
    }
    @Override
    public void destroy() throws Exception {
        log.info("Destroy: Destroying " + articles.size() + " articles.");
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }

}

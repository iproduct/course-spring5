package org.iproduct.spring.programmatic;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
@PropertySource("articles.properties")
public class MockArticleProvider implements ArticleProvider, InitializingBean {
    @Autowired()
    Environment env;

    @Value("${listOfValues}")
    private String[] articleTitles;
    public MockArticleProvider(){
    }

    public MockArticleProvider(String[] articleTitles){
        this.articleTitles = articleTitles;
    }

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
            new Article(articleTitles[0], "Spring 5 is great beacuse ..."),
            new Article(articleTitles[1], "Should I use DI or lookup ..."),
            new Article(articleTitles[2], "There are several ways to configure Spring beans.")
        );
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
//        articleTitles = env.getProperty("listOfValues", String[].class);
//        this.articleTitles = new String[]{"X", "Y", "Z"};
    }
}

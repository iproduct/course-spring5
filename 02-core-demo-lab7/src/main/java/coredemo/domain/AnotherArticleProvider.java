package coredemo.domain;

import coredemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("alternativeProvider")
@PropertySource("articles.properties")
public class AnotherArticleProvider implements ArticleProvider {
    @Value("${numberOfArticles}")
    private int articleCount;

    @Autowired
    ApplicationContext context;

    @Override
    public List<Article> getArticles() {
        System.out.println(articleCount);
        List<Article> articles = new ArrayList<>();
        for(int i = 0; i < articleCount; i++) {
            articles.add(context.getBean("article", Article.class));
        }
        return articles;
    }
}

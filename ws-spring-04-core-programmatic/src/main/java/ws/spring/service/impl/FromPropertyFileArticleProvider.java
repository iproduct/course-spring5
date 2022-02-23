package ws.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ws.spring.model.Article;
import ws.spring.service.ArticleProvider;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Qualifier("fromPropertyProvider")
public class FromPropertyFileArticleProvider implements ArticleProvider {
    private List<Article> articles;
    private Environment environment;

    @Autowired
    public FromPropertyFileArticleProvider(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init(){
       String titles = environment.getProperty("blogs.titles");
       articles = Arrays.asList(titles.split(", ")).stream().map(
                        title -> new Article(System.nanoTime() & (new Random().nextLong()),
                                title, title + " content ...")).collect(Collectors.toList());
    }
//    public FromPropertyFileArticleProvider(List<Article> articles) {
//        this.articles = articles;
//    }

    @Override
    public Collection<Article> getArticles() {
        return articles;
    }
}

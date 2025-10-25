package course.spring.coredemo.service.impl;

import course.spring.coredemo.model.Article;
import course.spring.coredemo.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service("propertyArticleProvider")
@Qualifier("fromProperties")
//@PropertySource("classpath:articles.properties")
public class PropertyFileArticleProvider implements ArticleProvider {
    @Value("${articles.titles}")
    private String[] articlesTitles;

    @Override
    public List<Article> getArticles() {
        return Arrays.stream(articlesTitles)
                .map(title ->
                        new Article(title, title + " content ...", "Trayan Iliev", "Programming",
                                new HashSet(Arrays.asList(title.split("\\W+")))))
                .collect(Collectors.toList());
    }

    @Override
    public Article createArticle(Article article) {
        return null;
    }
}

package course.spring.service.impl;

import course.spring.model.Article;
import course.spring.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("FROM_PROPS")
public class ArticleProviderFromProperties implements ArticleProvider {
    @Value("${articles.titles}")
    private String[] articleTitles;

    public void setArticleTitles(String[] articleTitles) {
        this.articleTitles = articleTitles;
    }

    @Override
    public List<Article> getArticles() {
        return Arrays.stream(articleTitles)
                .map(title -> new Article(title, title, "Trayan"))
                .collect(Collectors.toList());
    }

    @Override
    public Article addArticle(Article article) {
        return null;
    }
}

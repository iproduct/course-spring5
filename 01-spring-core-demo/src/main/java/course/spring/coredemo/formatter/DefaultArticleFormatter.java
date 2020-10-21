package course.spring.coredemo.formatter;

import course.spring.coredemo.model.Article;
import org.springframework.stereotype.Service;

public class DefaultArticleFormatter implements ArticleFormatter{
    @Override
    public String formatArticle(Article article) {
        return String.format("| %6d | %-20.20s | %-30.30s |",
                article.getId(), article.getTitle(), article.getContent());
    }
}

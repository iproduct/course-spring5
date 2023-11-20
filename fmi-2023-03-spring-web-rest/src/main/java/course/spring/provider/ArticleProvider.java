package course.spring.provider;

import course.spring.model.Article;

import java.util.List;

public interface ArticleProvider {
    Article createArticle(Article article);
    int getArticlesCount();
    List<Article> getArticles();
    Article getArticleById(long id);
}

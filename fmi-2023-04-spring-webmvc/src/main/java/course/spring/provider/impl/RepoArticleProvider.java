package course.spring.provider.impl;

import course.spring.dao.ArticleRepository;
import course.spring.model.Article;
import course.spring.model.User;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.RepoBacked;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static course.spring.model.Role.*;

@Service
@Qualifier("repoProvider")
@RepoBacked
public class RepoArticleProvider implements ArticleProvider {
    public static final User DEFAULT_USER = new User("Trayan", "Iliev", "trayan", "trayan123", Set.of(READER, AUTHOR, ADMIN));
    public static RepoArticleProvider create(ArticleRepository repository) {
        var provider =  new RepoArticleProvider();
        provider.setRepository(repository);
        return provider;
    }
    private ArticleRepository repository;

    @Inject
    public void setRepository(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Article createArticle(Article article) {
        article.setAuthor(DEFAULT_USER);
        return repository.create(article);
    }

    @Override
    public int getArticlesCount() {
        return repository.count();
    }

    @Override
    public List<Article> getArticles() {
        return repository.findAll();
    }

    @Override
    public Article getArticleById(long id) {
        return repository.findById(id).orElseThrow();
    }


}

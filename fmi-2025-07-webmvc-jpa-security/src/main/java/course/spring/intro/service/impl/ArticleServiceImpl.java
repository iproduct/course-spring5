package course.spring.intro.service.impl;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.entity.Article;
import course.spring.intro.exception.EntityNotFoundException;
import course.spring.intro.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getArticlesByTags(Set<String> tags) {
        return articleRepository.findByTagsContainingIgnoreCase(tags);
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Article with id " + id + " not found"));
    }

    @Override
    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Article article) {
        var foundArticle = getArticleById(article.getId());
        return articleRepository.save(article);
    }

    @Override
    public Article deleteArticleById(Long id) {
        var deletedArticle = getArticleById(id);
        articleRepository.deleteById(id);
        return deletedArticle;
    }

    @Override
    public long getArticleCount() {
        return articleRepository.count();
    }
}

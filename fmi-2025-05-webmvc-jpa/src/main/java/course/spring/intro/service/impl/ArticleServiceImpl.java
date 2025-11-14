package course.spring.intro.service.impl;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.entity.Article;
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
        return List.of();
    }

    @Override
    public Article getArticleById(int id) {
        return null;
    }

    @Override
    public Article addArticle(Article article) {
        return null;
    }

    @Override
    public Article updateArticle(Article article) {
        return null;
    }

    @Override
    public Article deleteArticleById(int id) {
        return null;
    }

    @Override
    public long getArticleCount() {
        return 0;
    }
}

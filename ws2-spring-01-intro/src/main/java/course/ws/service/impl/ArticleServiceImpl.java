package course.ws.service.impl;

import course.ws.dao.ArticleRepository;
import course.ws.entity.Article;
import course.ws.exception.EntityNotFoundException;
import course.ws.exception.InvalidEntityDataException;
import course.ws.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepo;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findAllArticles() {
        return articleRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Article findArticleById(Long id) {
        return articleRepo.findById(id).orElseThrow(()->new EntityNotFoundException(
                String.format("Article with ID='%d' not found.", id)
        ));
    }

    @Override
    public Article create(Article article) {
        if(article.getId() != null) {
            throw new InvalidEntityDataException("New Article ID should be NULL.");
        }
        return articleRepo.save(article);
    }

    @Override
    public Article update(Article article) {
        Article old = findArticleById(article.getId());
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articleRepo.save(article);
    }

    @Override
    public Article deleteArticleById(Long id) {
        Article deleted = findArticleById(id);
        articleRepo.deleteById(id);
        return deleted;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return articleRepo.count();
    }
}

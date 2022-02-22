package course.ws.blogs.service.impl;

import course.ws.blogs.dao.ArticleRepository;
import course.ws.blogs.entity.Article;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Article getById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Article with ID='%s' not found", id)));
    }

    @Override
    public Article create(Article article) {
        if(article.getId() != null) {
            throw new InvalidEntityDataException(
                    String.format("Article %d: '%s' should not have ID during creation.",
                            article.getId(), article.getTitle()));
        }
        return articleRepository.save(article);
    }

    @Override
    public Article update(Article article) {
        Article old = getById(article.getId());
        return articleRepository.save(article);
    }

    @Override
    public Article deleteById(Long id) {
        Article old = getById(id);
        articleRepository.deleteById(id);
        return old;
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        return articleRepository.count();
    }
}

package course.spring.intro.service.impl;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.entity.Article;
import course.spring.intro.entity.Category;
import course.spring.intro.exception.EntityNotFoundException;
import course.spring.intro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepo;

    @Override
    public Article create(Article article) {
        return articleRepo.save(article);
    }

    @Override
    public Article update(Article article) throws EntityNotFoundException {
        var existing = findById(article.getId());
        return articleRepo.save(article);
    }

    @Override
    public Article delete(Long articleId) throws EntityNotFoundException {
        var existing = findById(articleId);
        articleRepo.delete(existing);
        return existing;
    }

    @Override
    public List<Article> findAll() {
        return articleRepo.findAll();
    }

    @Override
    public Article findById(Long articleId) throws EntityNotFoundException {
        return articleRepo.findById(articleId).orElseThrow(() -> new EntityNotFoundException(
                String.format("Article with id: %s not found", articleId)
        ));
    }

    @Override
    public List<Article> findByCategory(Category category) {
        return articleRepo.findByCategoriesIn(Set.of(category));
    }

    @Override
    public List<Article> findByTags(Set<String> tags) {
        return articleRepo.findByTagsIn(tags);
    }

    @Override
    public long size() {
        return articleRepo.count();
    }
}

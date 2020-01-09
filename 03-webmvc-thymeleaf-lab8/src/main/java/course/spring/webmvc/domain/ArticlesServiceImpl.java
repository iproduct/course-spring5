package course.spring.webmvc.domain;

import course.spring.restmvc.exception.NonexisitngEntityException;
import course.spring.webmvc.dao.ArticlesRepository;
import course.spring.webmvc.dao.UsersRepository;
import course.spring.webmvc.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticlesServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesRepository articlesRepository;
    private UsersRepository usersRepository;

    @Override
    public List<Article> findAll() {
        return articlesRepository.findAll();
    }

    @Override
    public Article findById(String articleId) {
        return articlesRepository.findById(articleId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Article with ID='%s' does not exist.", articleId)));
    }

    @Override
    public Article add(Article article) {
        return articlesRepository.insert(article);
    }

    @Override
    public Article update(Article article) {
        Optional<Article> old = articlesRepository.findById(article.getId());
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Article with ID='%s' does not exist.", article.getId()));
        }
        article.setCreated(old.get().getCreated());
        article.setModified(LocalDateTime.now());
        return articlesRepository.save(article);
    }

    @Override
    public Article remove(String articleId) {
        Optional<Article> old = articlesRepository.findById(articleId);
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Article with ID='%s' does not exist.", articleId));
        }
        articlesRepository.deleteById(articleId);
        return old.get();
    }

    @Override
    public long count() {
        return articlesRepository.count();
    }
}

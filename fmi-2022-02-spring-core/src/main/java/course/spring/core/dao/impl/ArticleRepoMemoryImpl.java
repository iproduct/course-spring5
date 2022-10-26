package course.spring.core.dao.impl;

import course.spring.core.dao.ArticleRepository;
import course.spring.core.dao.IdGenerator;
import course.spring.core.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ArticleRepoMemoryImpl extends RepositoryImpl<Article, Long> implements ArticleRepository  {

    @Autowired
    public ArticleRepoMemoryImpl(IdGenerator<Long> idGen) {
        super(idGen);
    }

    @Override
    public List<Article> findByTags(Collection<String> tags) {
        return findAll().stream()
                .filter(article -> article.getTags().containsAll(tags))
                .collect(Collectors.toList());
    }
}

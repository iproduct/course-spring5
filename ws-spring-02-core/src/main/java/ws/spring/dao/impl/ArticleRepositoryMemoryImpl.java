package ws.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ws.spring.dao.ArticleRepository;
import ws.spring.dao.IdGenerator;
import ws.spring.model.Article;

@Repository("articleRepo")
public class ArticleRepositoryMemoryImpl extends RepositoryMemoryImpl<Article, Long>
        implements ArticleRepository {
    public ArticleRepositoryMemoryImpl(IdGenerator<Long> generator) {
        super(generator);
    }
}

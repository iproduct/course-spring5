package course.ws.dao.impl;

import course.ws.dao.ArticleRepository;
import course.ws.dao.IdGenerator;
import course.ws.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("articleRepo")
public class ArticleRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, Article> implements ArticleRepository {
    @Autowired
    public ArticleRepositoryMemoryImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }
}

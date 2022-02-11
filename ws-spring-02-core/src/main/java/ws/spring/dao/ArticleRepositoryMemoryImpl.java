package ws.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ws.spring.model.Article;

@Repository("articleRepo")
public class ArticleRepositoryMemoryImpl extends RepositoryMemoryImpl<Article, Long>
        implements ArticleRepository {

    @Autowired
    public ArticleRepositoryMemoryImpl(IdGenerator<Long> generator) {
        super(generator);
        System.out.println(generator);
    }
}

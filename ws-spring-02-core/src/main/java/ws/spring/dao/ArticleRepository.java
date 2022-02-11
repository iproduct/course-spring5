package ws.spring.dao;

import org.springframework.stereotype.Repository;
import ws.spring.model.Article;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends GenericRepository<Article, Long>{
}

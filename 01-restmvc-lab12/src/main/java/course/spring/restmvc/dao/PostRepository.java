package course.spring.restmvc.dao;

import course.spring.restmvc.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Iterable<Post> findAllByKeywordsContaining(Iterable<String> keywords);
}

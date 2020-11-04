package course.spring.restmvc.dao;

import course.spring.restmvc.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> { }

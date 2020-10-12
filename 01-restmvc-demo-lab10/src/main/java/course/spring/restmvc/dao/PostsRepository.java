package course.spring.restmvc.dao;

import course.spring.restmvc.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository {
    List<Post> findAll();
    Post findById(Long id);
    Post create(Post post);
    Post update(Post post);
    Post deleteById(Long id);
    long count();
}

package course.spring.intro.dao;

import course.spring.intro.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository {
    List<Post> findAll();
    Post findById(String id);
    Post create(Post post);
    Post update(Post post);
    Post deleteById(String id);
    long count();
}

package course.spring.blogs.dao;

import course.spring.blogs.entity.Post;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> findAll();
    Optional<Post> findById(Long id);
    Post create(Post post);
    Post update(Post post);
    Post deleteById(Long id);
    long count();
}

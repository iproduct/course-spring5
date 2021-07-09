package course.spring.blogs.dao;

import course.spring.blogs.entity.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository {
    Collection<Post> findAll();
    Optional<Post> findById(Long id);
    Post create(Post post);
    Post update(Post post);
    Post deleteById(Long id);
    long count();
}

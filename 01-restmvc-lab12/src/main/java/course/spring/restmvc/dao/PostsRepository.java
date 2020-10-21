package course.spring.restmvc.dao;

import course.spring.restmvc.model.Post;

import java.util.List;

public interface PostsRepository {
    List<Post> findAll();
    Post findById(String id);
    Post create(Post post);
    Post update(Post post);
    Post deleteById(String id);
    long count();
}

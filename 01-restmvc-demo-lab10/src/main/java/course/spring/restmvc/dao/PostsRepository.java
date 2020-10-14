package course.spring.restmvc.dao;

import course.spring.restmvc.model.Post;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

public interface PostsRepository {
    List<Post> findAll();
    Post findById(Long id);
    Post create(@Valid Post post);
    Post update(Post post);
    Post deleteById(Long id);
    long count();
}

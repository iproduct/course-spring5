package course.spring.dao;

import course.spring.model.Post;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PostRepository extends Repository<Post, Long>{
    List<Post>  findByTags(Collection<String> tags);
}

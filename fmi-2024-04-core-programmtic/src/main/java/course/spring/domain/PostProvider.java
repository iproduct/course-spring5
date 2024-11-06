package course.spring.domain;

import course.spring.model.Post;

import java.util.Collection;
import java.util.List;

public interface PostProvider {
    List<Post> getAllPosts();
    List<Post> getByTags(Collection<String> tags);

    Post addPost(Post post);
}

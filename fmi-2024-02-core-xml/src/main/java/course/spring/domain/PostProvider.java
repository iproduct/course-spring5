package course.spring.domain;

import course.spring.model.Post;

import java.util.List;

public interface PostProvider {
    List<Post> getAllPosts();
}

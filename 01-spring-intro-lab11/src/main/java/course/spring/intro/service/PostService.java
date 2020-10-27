package course.spring.intro.service;

import course.spring.intro.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(String id);
    Post createPost(Post post);
    Post updatePost(Post post);
    Post deletePostById(String id);
    long getCount();
}

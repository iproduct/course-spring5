package course.spring.blog.service;

import course.spring.blog.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post addPost(Post post);
    Post updatePost(Post post);
    Post deletePostById(Long id);
    long getPostsCount();
}

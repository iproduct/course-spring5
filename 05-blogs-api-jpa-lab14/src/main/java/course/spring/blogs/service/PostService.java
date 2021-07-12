package course.spring.blogs.service;

import course.spring.blogs.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post addPost(Post post);
    long getCount();
}

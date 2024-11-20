package course.spring.blog.service;

import course.spring.blog.dto.PostCreateDto;
import course.spring.blog.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post addPost(PostCreateDto post);
    Post updatePost(PostCreateDto post);
    Post deletePostById(Long id);
    long getPostsCount();
}

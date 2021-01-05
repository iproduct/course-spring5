package course.spring.myblogsapp.service;

import course.spring.myblogsapp.entity.Post;

import javax.validation.Valid;
import java.util.List;

public interface PostService {
    List<Post>  getAllPosts();
    Post  getPostById(Long id);
    Post addPost(@Valid Post post);
    Post updatePost(@Valid Post post);
    Post deletePost(Long id);
    long getPostsCount();
}

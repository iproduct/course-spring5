package course.spring.restmvc.service;

import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.User;

import java.util.Collection;

public interface PostService {
    Collection<Post>  getAllPosts();
    Post getPostById(Long id);
    Post addPost(Post post);
    Post updatePost(Post post);
    Post deletePost(Long id);
    long getCount();
}

package course.spring.restmvc.service;

import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id) throws NonexistingEntityException;
    Post createPost(Post post) throws InvalidEntityDataException;
    Post updatePost(Post post) throws InvalidEntityDataException, NonexistingEntityException;
    Post deletePost(Long id) throws NonexistingEntityException;
    long count();
}

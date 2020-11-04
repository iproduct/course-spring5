package course.spring.restmvc.service;

import course.spring.restmvc.dto.PostDto;
import course.spring.restmvc.entity.Post;

import javax.validation.Valid;
import java.util.Collection;

public interface PostService {
    Collection<Post>  getAllPosts();
    Post getPostById(Long id);
    Post addPost(Post post);
    Post addPostDto(@Valid PostDto postDto);
    Post updatePost(@Valid PostDto post);
    Post deletePost(Long id);
    long getCount();


}

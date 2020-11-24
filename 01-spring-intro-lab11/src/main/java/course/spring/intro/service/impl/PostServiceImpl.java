package course.spring.intro.service.impl;

import course.spring.intro.dao.PostRepository;
import course.spring.intro.dao.PostRepositoryOld;
import course.spring.intro.dao.UserRepository;
import course.spring.intro.exception.EntityNotFoundException;
import course.spring.intro.model.Post;
import course.spring.intro.model.User;
import course.spring.intro.service.PostService;
import course.spring.intro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(String id) {
        return postRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format("Post with ID=%s not found.", id)));
    }

    @Override
    public Post createPost(Post post) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userService.getUserByUsername(username);
        post.setAuthorId(author.getId());
        return postRepository.insert(post);
    }

    @Override
    public Post updatePost(Post post) {
        getPostById(post.getId());
        return postRepository.save(post);
    }

    @Override
    public Post deletePostById(String id) {
        Post removed = getPostById(id);
        postRepository.deleteById(id);
        return removed;
    }

    @Override
    public long getCount() {
        return postRepository.count();
    }

}

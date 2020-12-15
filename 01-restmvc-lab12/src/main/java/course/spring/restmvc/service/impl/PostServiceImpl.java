package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.Post;
import course.spring.restmvc.model.User;
import course.spring.restmvc.service.PostService;
import course.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;
    private UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepo, UserService userService) {
        this.postRepo = postRepo;
        this.userService = userService;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public List<Post> getAllPostsByKeywords(Set<String> keywords) {
        return postRepo.findAllByKeywordsContaining(keywords);
    }

    @Override
    public Post getPostById(String id) {
        return postRepo.findById(id).orElseThrow(() ->
            new NonexistingEntityException(String.format("Post with ID:%s does not exist.", id)));
    }

    @Override
    public Post addPost(Post post) {
        post.setId(null);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userService.getUserByUsername(username);
        post.setAuthorId(author.getId());
        return postRepo.insert(post);
    }

    @Override
    public Post updatePost(Post post) {
        getPostById(post.getId());
        post.setModified(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public Post deletePost(String id) {
        Post removed = getPostById(id);
        postRepo.deleteById(id);
        return removed;
    }

    @Override
    public long getCount() {
        return postRepo.count();
    }
}

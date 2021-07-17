package course.spring.blogs.service.impl;

import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.Post;
import course.spring.blogs.entity.User;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.exception.NonexistingEntityException;
import course.spring.blogs.exception.AuthorNotFoundException;
import course.spring.blogs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new NonexistingEntityException(String.format("Post with ID=%d does not exist", id))
        );
    }

    @Override
    public Post addPost(Post post) {
        if (post.getAuthor() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String username = authentication.getName();
                User currentUser = userRepository.findByUsername(username).orElseThrow(() ->
                        new AuthorNotFoundException(String.format("User '%s' can not be found", username)));
                post.setAuthor(currentUser);
            } else {
                throw new AuthorNotFoundException("Anonymous users can not create posts.");
            }
        }
        post.setId(null);
        Post created = postRepository.save(post);
        post.getAuthor().getPosts().add(post);
        return created;
    }

    @Override
    @PreAuthorize("#post.author.id == authentication.principal.id or hasRole('ADMIN')")
    public Post updatePost(Post post) {
        Post old = getPostById(post.getId());
        if (!post.getAuthor().equals(old.getAuthor())) {
            throw new InvalidEntityDataException(String.format("Post author can not be changed from ID=%d to ID=%d",
                    old.getId(), post.getId()));
        }
        Post updated = postRepository.save(post);
        return updated;
    }

    @Override
    public Post deletePostById(Long id) {
        Post deleted = getPostById(id);
        deleted.getAuthor().getPosts().remove(deleted);
        postRepository.deleteById(id);
        return deleted;
    }

    @Override
    public long getPostsCount() {
        return postRepository.count();
    }
}

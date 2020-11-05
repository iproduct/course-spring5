package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.CategoryRepository;
import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.dao.UserRepository;
import course.spring.restmvc.dto.PostDto;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.User;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static course.spring.restmvc.util.ErrorHandlingUtil.hanleConstraintViolationException;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public Collection<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Post with ID:%s not found.", id)));
    }

    @Override
    public Post addPost(@Valid Post post) {
        post.setId(null);
        post.setCreated(LocalDateTime.now());
        post.setModified(LocalDateTime.now());
        post.getAuthor().getPosts().add(post);
        post.getCategories().forEach(category -> category.getPosts().add(post));
        return postRepo.save(post);
    }

    @Override
    public Post addPostDto(@Valid PostDto postDto) {
        Post post = getPostAndResolvePostCategories(postDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Optional<User> author = userRepo.findByUsername(authentication.getName());
            post.setAuthor(author.get());
        }

        return addPost(post);
    }

    @Override
    public Post updatePost(PostDto postDto) {
        Post oldPost = getPostById(postDto.getId());
        Post post = getPostAndResolvePostCategories(postDto);

        // Remove old delta categories
        Set<Category> categoriesToRemove = new HashSet<>( oldPost.getCategories());
        categoriesToRemove.removeAll(post.getCategories());
        categoriesToRemove.forEach(category -> category.getPosts().remove(oldPost));
        // Add new delta categories
        Set<Category> categoriesToAdd = new HashSet<>( post.getCategories());
        categoriesToAdd.removeAll(oldPost.getCategories());
        post.getCategories().forEach(category -> category.getPosts().add(post));

        post.setModified(LocalDateTime.now());
        try {
            return postRepo.save(post);
        } catch (RuntimeException ex) {
            hanleConstraintViolationException(ex);
            return null;
        }
    }

    @Override
    public Post deletePost(Long id) {
        Post deleted = postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Post with ID:%s not found.", id)));
        deleted.getCategories().forEach(category -> category.getPosts().remove(deleted));
        postRepo.deleteById(id);
        return deleted;
    }

    @Override
    public long getCount() {
        return postRepo.count();
    }

    // Helper methods
    private Post getPostAndResolvePostCategories(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        Set<Category> categories = categoryRepo.findByTitleInIgnoreCase(postDto.getCategoryTitles());
        if(categories.size() == 0) {
            throw new InvalidEntityDataException(
                    String.format("The post %d:%s must have at least one valid category, but none was found.",
                            post.getId(), post.getTitle()));
        }
        post.setCategories(categories);
        return post;
    }
}

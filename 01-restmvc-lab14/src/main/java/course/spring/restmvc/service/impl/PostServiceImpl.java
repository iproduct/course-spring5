package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.CategoryRepository;
import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.dao.UserRepository;
import course.spring.restmvc.dto.PostDTO;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.User;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class PostServiceImpl implements PostService {
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
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
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
    public Post addPostDto(@Valid PostDTO postDto) {
        ModelMapper mapper = new ModelMapper();
        Post post = mapper.map(postDto, Post.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Optional<User> author = userRepo.findByUsername(authentication.getName());
            post.setAuthor(author.get());
        }
        Set<Category> categories = categoryRepo.findByTitleInIgnoreCase(postDto.getCategoryTitles());
        post.setCategories(categories);
        return addPost(post);
    }

    @Override
    public Post updatePost(Post post) {
        Post found = getPostById(post.getId());
        post.setModified(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public Post deletePost(Long id) {
        Post deleted = postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Post with ID:%s not found.", id)));
        postRepo.deleteById(id);
        return deleted;
    }

    @Override
    public long getCount() {
        return postRepo.count();
    }
}

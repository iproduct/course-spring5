package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.PostsJpaRepository;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.Post;
import course.spring.restmvc.service.PostService;
import course.spring.restmvc.util.ExceptionHandlingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Slf4j
@Transactional(propagation=REQUIRED)
public class PostServiceImpl implements PostService {
    private PostsJpaRepository postsRepo;

    @Autowired
    public PostServiceImpl(PostsJpaRepository postsRepo) {
        this.postsRepo = postsRepo;
    }

    @Autowired
    private Validator validator;

    @Override
    public List<Post> getAllPosts() {
        return postsRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) throws NonexistingEntityException {
        Post result = postsRepo.findById(id).orElseThrow(
                () -> new NonexistingEntityException(String.format("Post with ID:%d does not exist", id)));
        return result;
    }

    @Override
    public Post createPost(Post post) {
//        Errors errors = new BindException(post, "Post");
//        Set<ConstraintViolation<Post>> violations = validator.validate(post);
//        if(!violations.isEmpty()) {
//            throw new ValidationErrorsException(violations);
//        }
        post.setId(null);
        post.setCreated(LocalDateTime.now());
        post.setModified(LocalDateTime.now());
        Post result = null;
        try {
            result = postsRepo.save(post);
        } catch(RuntimeException e) {
            // log.error("!!!!!!  Exception catched:", ex);
            ExceptionHandlingUtils.hanleConstraintViolationException(e);
        }
        return result;
    }

    @Override

    public Post updatePost(Post post) throws InvalidEntityDataException, NonexistingEntityException {
        Post result = postsRepo.findById(post.getId()).orElseThrow(
                ()->new NonexistingEntityException(String.format("Post with ID:%d does not exist", post.getId())));
        if(!result.getCreated().equals(post.getCreated())) {
            throw new InvalidEntityDataException(String.format("Post creation date: %s can not be modified.",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(result.getCreated())));
        }
        post.setModified(LocalDateTime.now());
        return postsRepo.save(post);
    }

    @Override
    public Post deletePost(Long id) throws NonexistingEntityException{
        Post result = getPostById(id);
        postsRepo.deleteById(id);
        return result;
    }

    @Override
    public long count() {
        return postsRepo.count();
    }


}

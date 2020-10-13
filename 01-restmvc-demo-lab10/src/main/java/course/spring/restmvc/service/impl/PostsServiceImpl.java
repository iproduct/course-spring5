package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.PostsRepository;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.Post;
import course.spring.restmvc.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostsServiceImpl implements PostsService {
    private PostsRepository postsRepo;

    @Autowired
    public PostsServiceImpl(PostsRepository postsRepo) {
        this.postsRepo = postsRepo;
    }

    @Override
    public List<Post> getAllPosts() {
        return postsRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) throws NonexistingEntityException {
        Post result = postsRepo.findById(id);
        if (result == null) {
            throw new NonexistingEntityException(String.format("Post with ID:%d does not exist", id));
        }
        return result;
    }

    @Override
    public Post createPost(Post post) {
        post.setCreated(LocalDateTime.now());
        post.setModified(LocalDateTime.now());
        return postsRepo.create(post);
    }

    @Override

    public Post updatePost(Post post) throws InvalidEntityDataException, NonexistingEntityException {
        Post result = postsRepo.findById(post.getId());
        if(!result.getCreated().equals(post.getCreated())) {
            throw new InvalidEntityDataException(String.format("Post creation date: %s can not be modified.",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(result.getCreated())));
        }
        result = postsRepo.update(post);
        if (result == null) {
            throw new NonexistingEntityException(String.format("Post with ID:%d does not exist", post.getId()));
        }
        post.setModified(LocalDateTime.now());
        return result;
    }

    @Override
    public Post deletePost(Long id) throws NonexistingEntityException{
        Post result = postsRepo.deleteById(id);
        if (result == null) {
            throw new NonexistingEntityException(String.format("Post with ID:%d does not exist", id));
        }
        return result;
    }

    @Override
    public long count() {
        return postsRepo.count();
    }
}

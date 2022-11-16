package course.spring.webmvc.service.impl;

import course.spring.webmvc.dao.PostRepository;
import course.spring.webmvc.entity.Post;
import course.spring.webmvc.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsServiceImpl implements PostsService {
    private PostRepository postRepo;

    @Autowired
    public PostsServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

}

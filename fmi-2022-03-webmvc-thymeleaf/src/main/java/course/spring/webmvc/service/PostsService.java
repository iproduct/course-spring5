package course.spring.webmvc.service;

import course.spring.webmvc.dao.PostRepository;
import course.spring.webmvc.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostsService {
    List<Post> getAllPosts();
    Post add(Post post);
    Post update(Post post);
}

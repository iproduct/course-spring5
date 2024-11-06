package course.spring.domain.impl;

import course.spring.domain.PostProvider;
import course.spring.model.Post;
import course.spring.qualifiers.SimpleProvider;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log
@Service("propsProvider")
@Order(3)
public class PropertyFilePostProvider implements PostProvider {

//    @Value("${posts.titles}")
    private String[] titles;

    @Autowired
    Environment environment;

    @Override
    public List<Post> getAllPosts() {
        var titlesStr = environment.getProperty("posts.titles");
        titles = titlesStr.split(",\\s+");
        log.info(Arrays.stream(titles).collect(Collectors.joining()));
        var rand = new Random();
        return Arrays.stream(titles).map(title ->
                new Post(rand.nextLong(), title, title + " contnent ...",
                Arrays.stream(title.split("\\W+")).collect(Collectors.toSet()))
        ).toList();
    }

    @Override
    public List<Post> getByTags(Collection<String> tags) {
        return getAllPosts() ;
    }

    @Override
    public Post addPost(Post post) {
        return post;
    }
}

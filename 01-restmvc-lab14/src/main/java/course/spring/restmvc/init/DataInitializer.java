package course.spring.restmvc.init;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.User;
import course.spring.restmvc.service.CategoryService;
import course.spring.restmvc.service.PostService;
import course.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.restmvc.entity.Role.*;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if(userService.getCount() == 0) {
            List.of(
                    new User("Default", "Admin", "admin@default.com", "admin", "admin123", Set.of(READER, AUTHOR, ADMIN)),
                    new User("Default", "Author", "author@default.com", "author", "author123", Set.of(READER, AUTHOR)),
                    new User("Default", "Reader", "reader@default.com", "reader", "reader123")
            ).forEach(userService::addUser);
        }

        if(categoryService.getCount() == 0 && postService.getCount() == 0 && userService.getCount() >= 2) {

            List<Category> categories = List.of(
                    new Category("Java EE", "Java Enterprise Edition"),
                    new Category("Spring", "Java Spring Platofrm"),
                    new Category("Reactive Programming", "High-performance functional declarative programming")
            ).stream().map(categoryService::addCategory).collect(Collectors.toList());

            List<User> users = userService.getAllUsers();
            List.of(
                    new Post("New in Spring", "Spring 5 is here ...", users.get(0),
                            Set.of("spring", "java", "webflux"), Set.of(categories.get(1), categories.get(2))),
                    new Post("DI in Spring", "Many ways to do it ...", users.get(1),
                            Set.of("spring", "di"), Set.of(categories.get(1))),
                    new Post("Reactive Web Programmiing", "Webflux uses Reactor ...", users.get(1),
                            Set.of("spring", "reactor", "webflux"), Set.of(categories.get(1), categories.get(2))),
                    new Post("JPA Made Easy", "Spring Data DSL automates JPQL creation ...", users.get(1),
                            Set.of("spring", "spring data", "jpql"), Set.of(categories.get(0), categories.get(1)))
            ).forEach(postService::addPost);
        }
    }
}

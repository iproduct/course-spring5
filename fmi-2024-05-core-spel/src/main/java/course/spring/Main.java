package course.spring;

import course.spring.config.AppConfig;
import course.spring.domain.PostProvider;
import course.spring.domain.UserService;
import course.spring.model.Post;
import course.spring.presenter.PostPresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
//        ApplicationContext ctx = new AnnotationConfigApplicationContext("course.spring");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        // Lookup using Service Locator pattern
//        PostProvider provider = ctx.getBean("repoProvider", PostProvider.class);
//        provider.addPost(new Post("New Post 1", "New Post 1 content", Set.of("new", "post", "one")) );
//        provider.addPost(new Post("New Post 2", "New Post 2 content", Set.of("new", "post", "two")) );
//        provider.addPost(new Post("New Post 3", "New Post 3 content", Set.of("new", "post", "three")) );
        PostPresenter presenter = ctx.getBean("presenter", PostPresenter.class);
        presenter.present();
        // Lookup users using Service Locator pattern
        UserService userService = ctx.getBean(UserService.class);
        userService.getAllUsers().forEach(System.out::println);
    }
}

package course.hibernate.spring.init;

import course.hibernate.spring.entity.User;
import course.hibernate.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static course.hibernate.spring.entity.Role.*;

//@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {
    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "Admin123&",
                    Set.of(READER, AUTHOR, ADMIN)),
            new User("Default", "Author", "author", "Author123&",
                    Set.of(READER, AUTHOR)),
            new User("Default", "Reader", "reader", "Reader123&")
    );
    private final UserService userService;

    @Autowired
    public DataInitializer(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userService.count() == 0) {
            try {
                List<User> created = userService.createBatch(SAMPLE_USERS);
                SAMPLE_USERS.forEach(u -> {
//                    u.setUserInfo(new UserInfo(u,"Java, Spring, Hibernate"));
                    userService.update(u);
                });

                log.info("Created default users: {}", created);
            } catch (ConstraintViolationException ex) {
                log.error(">>> Constraint violation inserting users: {} - {}", SAMPLE_USERS, ex.getMessage());
            }
            long countAfter = userService.count();
            log.info("Users count: {}", countAfter);
        }
    }
}

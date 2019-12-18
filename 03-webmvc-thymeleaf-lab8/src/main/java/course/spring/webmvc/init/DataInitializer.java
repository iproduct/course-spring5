package course.spring.restmvc.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DataInitializer implements ApplicationRunner {
//    @Autowired
//    private UsersService usersService;
//
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        log.info("Initializing application...");
//        if (usersService.count() == 0) {
//            User user = new User(null,"admin", "admin123&", "Admin", "Admin", "ROLE_ADMIN",
//                    true, LocalDateTime.now(), LocalDateTime.now());
//            log.info("Creating root admin user: {}", user.getUsername());
//            usersService.add(user);
//        }
    }

}

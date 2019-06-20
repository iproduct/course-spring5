package bookstore.init;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import bookstore.exception.EntityExistsException;
import bookstore.model.User;
import bookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataInItializer implements CommandLineRunner{
	@Autowired
	private UserService userService;

	@Override
	public void run(String... args) throws Exception {
		
		int usersCount = userService.getAll().size();
		
		if (usersCount == 0) {
            List<User> users = Arrays.asList(new User[] {
                new User("admin", "admin", "DEFAULT", "ADMIN", "ROLE_ADMIN"),
                new User("ivan", "ivan", "Ivan", "Petrov", "ROLE_USER")
            });

            users.forEach(u -> {
				try {
					userService.create(u);
				} catch (EntityExistsException e) {
					log.error("Error creating User:", e);
				}
			});
        }

        log.info("Querying for user records:");
        userService.getAll()
        	.forEach(user -> log.info(user.toString()));
		
	}

}

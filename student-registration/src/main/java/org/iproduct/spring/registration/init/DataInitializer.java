package org.iproduct.spring.registration.init;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.registration.model.User;
import org.iproduct.spring.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    UserService userService;


    @Override
    public void run(String... args) throws Exception {


        List<User> defaultUsers = Arrays.asList(
                new User("admin", "admin", "DEFAULT", "ADMIN", "admin@gamil.com", "ADMIN"),
                new User("ivan", "ivan123", "Ivan", "Petrov", "ivan@gamil.com", "USER")
        );
        System.out.println(defaultUsers);
        defaultUsers.stream().forEach(userService::addUser);


        log.info("Querying for user records:");
        List<User> users = userService.getAllUsers();
        users.forEach(user -> log.info("{}", user.getUsername()));
    }
}



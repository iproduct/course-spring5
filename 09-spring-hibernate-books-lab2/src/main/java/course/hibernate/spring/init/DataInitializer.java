package course.hibernate.spring.init;

import course.hibernate.spring.dao.BookRepository;
import course.hibernate.spring.dao.SubsystemRepository;
import course.hibernate.spring.entity.*;
import course.hibernate.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
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
    private final SubsystemRepository subsystemRepo;
    private final BookRepository bookRepo;

    @Autowired
    public DataInitializer(UserService userService, SubsystemRepository subsystemRepo, BookRepository bookRepo) {
        this.userService = userService;
        this.subsystemRepo = subsystemRepo;
        this.bookRepo = bookRepo;
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
        // books demo
        Book b1 = new Book("Effective Java", List.of(new Author("Joshua", "Bloch",
                LocalDate.of(1965, 8, 11))), "978-0134685991");
        bookRepo.save(b1);

        // Subsystem User demo
        Subsystem ss1 = subsystemRepo.createSubsystem(new Subsystem("Internal Projects",
                "Management of internal projects subsystem"));
        log.info("Created Subsytem: {}", ss1);
        SystemUser su1 = subsystemRepo.createUser(new SystemUser(ss1.getId(), "john",
                "John Doe"));
        SystemUser found = subsystemRepo.findUserBySubsystemUsername(ss1.getId(), su1.getUsername());
        log.info("Created SystemUser: {}", found);

        // Subsystem User demo using EmbeddedId
        Subsystem ss2 = subsystemRepo.createSubsystem(new Subsystem("Client Projects",
                "Management of client projects subsystem"));
        log.info("Created Subsytem: {}", ss2);
        SystemUserEmbeddedId su2 = subsystemRepo.createUserEmbeddedId(
                new SystemUserEmbeddedId(new EmbeddedPK(ss2, "jane"),"Jane Doe"));
        SystemUserEmbeddedId su3 = subsystemRepo.createUserEmbeddedId(
                new SystemUserEmbeddedId(new EmbeddedPK(ss2, "ivan"),"Ivan Petrov"));
        SystemUserEmbeddedId found2 = subsystemRepo.findUserEmbeddedId(su2.getId());
        log.info("Created SystemUser: {}", found2);

    }
}

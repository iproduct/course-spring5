package course.spring.service.impl;

import course.spring.dao.UsersRepository;
import course.spring.model.User;
import course.spring.service.UsersProvider;

import java.util.List;
import java.util.Set;

import static course.spring.model.Category.AI;
import static course.spring.model.Category.PROGRAMMING;

public class UsersProviderInMemory implements UsersProvider {
    public static final List<User> SAMPLE_USERS =List.of(
            new User("trayan@gmail.com", "Trayan Iliev", "Sofia 1000"),
            new User("john@gmail.com", "John Doe", "London 2000"),
            new User("jane@gmail.com", "Jane Doe", "New York 3000")
    );

    private UsersRepository usersRepo;

    public UsersProviderInMemory(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    public void init(){
        SAMPLE_USERS.forEach(this::addUser);
    }


    @Override
    public List<User> getUsers() {
        return usersRepo.findAll();
    }

    @Override
    public User addUser(User user) {
        return usersRepo.create(user);
    }
}

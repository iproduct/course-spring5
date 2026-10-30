package course.spring.dao.impl;

import course.spring.dao.UsersRepository;
import course.spring.dao.IdGenerator;
import course.spring.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UsersRepositoryInMemory implements UsersRepository {
    private Map<Long, User> users = new HashMap<>();
    private IdGenerator<Long> idGen;

    public UsersRepositoryInMemory(IdGenerator<Long> idGen) {
        this.idGen = idGen;
    }

    public List<User> findAll() {
        return users.values().stream().collect(Collectors.toList());
    }

    @Override
    public User create(User user) {
        user.setId(idGen.getNextId());
        users.put(user.getId(), user);
        return user;
    }


}

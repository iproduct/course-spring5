package ws.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ws.spring.model.User;

import java.util.Optional;

@Repository("userRepo")
public class UserRepositoryMemoryImpl extends RepositoryMemoryImpl<User, Long>
        implements UserRepository {

    @Autowired
    public UserRepositoryMemoryImpl(IdGenerator<Long> generator) {
        super(generator);
        System.out.println(generator);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
}

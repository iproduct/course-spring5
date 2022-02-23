package course.ws.dao.impl;

import course.ws.dao.ArticleRepository;
import course.ws.dao.IdGenerator;
import course.ws.dao.UserRepository;
import course.ws.model.Article;
import course.ws.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository("userRepo")
public class UserRepositoryMemoryImpl extends RepositoryMemoryImpl<Long, User> implements UserRepository {
    @Autowired
    public UserRepositoryMemoryImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return findAll().stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}

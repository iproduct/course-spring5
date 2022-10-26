package course.spring.core.dao.impl;

import course.spring.core.dao.IdGenerator;
import course.spring.core.dao.UserRepository;
import course.spring.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepoMemoryImpl extends RepositoryImpl<User, Long> implements UserRepository  {

    @Autowired
    public UserRepoMemoryImpl(IdGenerator<Long> idGen) {
        super(idGen);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return findAll().stream()
                .filter(article -> article.getUsername().equals(username))
                .findAny();
    }
}

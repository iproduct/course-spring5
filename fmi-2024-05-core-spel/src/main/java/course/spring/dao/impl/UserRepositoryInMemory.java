package course.spring.dao.impl;

import course.spring.dao.KeyGenerator;
import course.spring.dao.PostRepository;
import course.spring.dao.UserRepository;
import course.spring.model.Post;
import course.spring.model.Role;
import course.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryInMemory extends InMemoryRepository<User, Long> implements UserRepository {
    @Autowired
    public UserRepositoryInMemory(KeyGenerator<Long> generator) {
        super(generator);
    }


    @Override
    public Optional<User> findByUsername(String username) {
        return findAll().stream().filter(user -> user.getUsername().equals(username)).findAny();
    }

    @Override
    public List<User> findByRoles(Collection<Role> roles) {
        var tagsSet = Set.copyOf(roles);
        return findAll().stream().filter(u -> {
            tagsSet.retainAll(u.getRoles());
            return !tagsSet.isEmpty();
        }).toList();
    }
}

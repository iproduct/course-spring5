package course.spring.dao.impl;

import course.spring.dao.ArticleRepository;
import course.spring.dao.IdGenerator;
import course.spring.dao.UserRepository;
import course.spring.model.Article;
import course.spring.model.Role;
import course.spring.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepositoryImpl extends RepositoryImpl<User, Long> implements UserRepository {
    public UserRepositoryImpl(IdGenerator<Long> idGen) {
        super(idGen);
    }

    @Override
    public List<User> findByRoles(Set<Role> roles) {
        return findAll().stream().filter(user -> {
            var rls = Set.of(user.getRoles());
            rls.retainAll(roles);
            return !rls.isEmpty();
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return findAll().stream().filter(user -> user.getUsername().equals(username)).findAny();
    }
}

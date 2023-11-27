package course.spring.dao;

import course.spring.model.Role;
import course.spring.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends MyRepository<User, Long> {
    List<User> findByRoles(Set<Role> role);
    Optional<User> findByUsername(String username);
}

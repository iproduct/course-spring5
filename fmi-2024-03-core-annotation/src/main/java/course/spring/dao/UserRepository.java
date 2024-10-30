package course.spring.dao;

import course.spring.model.Post;
import course.spring.model.Role;
import course.spring.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Long>{
    Optional<User> findByUsername(String username);
    List<User>  findByRoles(Collection<Role> roles);
}

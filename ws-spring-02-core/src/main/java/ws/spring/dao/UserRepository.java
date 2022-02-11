package ws.spring.dao;

import ws.spring.model.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User, Long>{
    Optional<User> findByUsername(String username);
}

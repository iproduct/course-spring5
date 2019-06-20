package bookstore.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
}

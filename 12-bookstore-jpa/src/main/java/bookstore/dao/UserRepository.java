package bookstore.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
}

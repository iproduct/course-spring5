package bookstore.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>{
	Optional<Author> findByFirstNameAndLastName(String fname, String lname);
}

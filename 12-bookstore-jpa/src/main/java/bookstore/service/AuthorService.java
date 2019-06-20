package bookstore.service;

import java.util.List;

import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.Author;

public interface AuthorService {
	List<Author> getAll();
	Author getById(int id) throws EntityNotFoundException;
	Author create(Author author) throws EntityExistsException;
	Author update(Author author) throws EntityNotFoundException;
	Author delete(int authorId) throws EntityNotFoundException;
}

package bookstore.service;

import java.util.List;

import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.Book;

public interface BookService {
	List<Book> getAll();
	Book getById(int id) throws EntityNotFoundException;
	Book create(Book book) throws EntityExistsException;
	Book update(Book book) throws EntityNotFoundException;
	Book delete(int bookId) throws EntityNotFoundException;
}

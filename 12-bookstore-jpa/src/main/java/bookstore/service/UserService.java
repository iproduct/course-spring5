package bookstore.service;

import java.util.List;

import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.User;

public interface UserService {
	List<User> getAll();
	User getById(int id) throws EntityNotFoundException;
	User getByUsername(String username) throws EntityNotFoundException;
	User create(User user) throws EntityExistsException;
	User update(User user) throws EntityNotFoundException;
	User delete(int userId) throws EntityNotFoundException;
}

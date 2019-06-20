package bookstore.service;

import java.util.List;

import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.Publisher;

public interface PublisherService {
	List<Publisher> getAll();
	Publisher getById(int id) throws EntityNotFoundException;
	Publisher create(Publisher publisher) throws EntityExistsException;
	Publisher update(Publisher publisher) throws EntityNotFoundException;
	Publisher delete(int publisherId) throws EntityNotFoundException;

}

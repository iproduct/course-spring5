package bookstore.service;

import java.util.List;

import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.Format;

public interface FormatService {
	List<Format> getAll();
	Format getById(int id) throws EntityNotFoundException;
	Format create(Format format) throws EntityExistsException;
	Format update(Format format) throws EntityNotFoundException;
	Format delete(int formatId) throws EntityNotFoundException;

}

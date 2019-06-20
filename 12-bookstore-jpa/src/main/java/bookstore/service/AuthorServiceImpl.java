package bookstore.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.dao.AuthorRepository;
import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.Author;

@Service
public class AuthorServiceImpl implements AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public List<Author> getAll() {
		return authorRepository.findAll();
	}

	@Override
	public Author getById(int id) throws EntityNotFoundException {
		return authorRepository.findById(id).orElseThrow(() -> 
		new EntityNotFoundException("Author with ID=" + id + " not found."));
	}

	@Override
	@Transactional
	public Author create(Author author) throws EntityExistsException {
		if (author.getId() > 0 && getById(author.getId()) != null)
			throw new EntityExistsException("Entity with ID=" + author.getId() + " already exists.");
		return authorRepository.save(author);
	}

	@Override
	@Transactional
	public Author update(Author author) throws EntityNotFoundException {
		getById(author.getId());
		return authorRepository.save(author);
	}

	@Override
	@Transactional
	public Author delete(int authorId) throws EntityNotFoundException {
		Author author = getById(authorId);
		authorRepository.deleteById(authorId);
		return author;
	}

}

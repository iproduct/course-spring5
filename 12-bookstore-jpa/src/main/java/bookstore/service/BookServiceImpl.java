package bookstore.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.dao.AuthorRepository;
import bookstore.dao.BookRepository;
import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.Author;
import bookstore.model.Book;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public List<Book> getAll() {
		return bookRepository.findAll();
	}

	@Override
	public Book getById(int id) throws EntityNotFoundException {
		return bookRepository.findById(id).orElseThrow(() -> 
		new EntityNotFoundException("Book with ID=" + id + " not found."));
	}

	@Override
	@Transactional
	public Book create(Book book) throws EntityExistsException {
		if (book.getId() > 0 && getById(book.getId()) != null)
			throw new EntityExistsException("Entity with ID=" + book.getId() + " already exists.");
		book.getAuthors().forEach(a -> {
			List<Book> authorBooks = a.getBooks();
			if(authorBooks != null && !authorBooks.contains(book)) {
				authorBooks.add(book);
			};
			authorRepository.save(a);
		});
		return bookRepository.save(book);
	}

	@Override
	public Book update(Book book) throws EntityNotFoundException {
		getById(book.getId());
		return bookRepository.save(book);
	}

	@Override
	public Book delete(int bookId) throws EntityNotFoundException {
		Book book = getById(bookId);
		bookRepository.deleteById(bookId);
		return book;
	}

}

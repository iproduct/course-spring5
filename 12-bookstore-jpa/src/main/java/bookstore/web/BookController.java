package bookstore.web;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.UriComponentsBuilder;

import bookstore.dto.AuthorSelection;
import bookstore.exception.EntityExistsException;
import bookstore.model.Author;
import bookstore.model.Book;
import bookstore.model.Format;
import bookstore.model.Publisher;
import bookstore.service.AuthorService;
import bookstore.service.BookService;
import bookstore.service.FormatService;
import bookstore.service.PublisherService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("books")
@SessionAttributes({"book", "bookAuthors"})
@Slf4j
public class BookController {
	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private PublisherService publisherService;
	
	@Autowired
	private FormatService formatService;
	
    @ModelAttribute("book")
    Book getBookModelAttribute() {
        return new Book();
    }

    @ModelAttribute("bookAuthors")
    List<Author> getBookAuthorsModelAttribute() {
        return new ArrayList<Author>();
    }
    
    @ModelAttribute("authors")
    List<Author> getAuthorsModelAttribute() {
        return authorService.getAll();
    }
    
    @ModelAttribute("publishers")
    List<Publisher> getPublishersModelAttribute() {
        return publisherService.getAll();
    }

    @ModelAttribute("formats")
    List<Format> getFormatsModelAttribute() {
        return formatService.getAll();
    }
    
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(true);
//        binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(dateFormat, true));
//    }
    
	@GetMapping
	private String getBooksList(Model model) {
		model.addAttribute("books", bookService.getAll());
		return "books-list";
	}

	@PostMapping(params = "edit")
	public String editBook(@RequestParam("edit") int editId, Model model, UriComponentsBuilder uriBuilder) {
		log.info("Editing book: " + editId);
		URI uri = uriBuilder.path("/books/book-form").query("mode=edit&bookId={id}").buildAndExpand(editId).toUri();
		return "redirect:" + uri.toString();
	}

    @PostMapping(params = "delete")
    public String deleteBook(@RequestParam("delete") int deleteId, Model model){
        log.info("Deleting book: " + deleteId);
        bookService.delete(deleteId);
        return "redirect:/books";
    }

	@GetMapping("/book-form")
	public String getBookForm(@ModelAttribute("book") Book book, 
			@ModelAttribute("newAuthor") Author newAuthor,
			@ModelAttribute("bookAuthors") List<Author> bookAuthors,
			HttpSession session, SessionStatus status, ModelMap model, 
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "bookId", required = false) Integer bookId) {
		String title = "Add New Book";
		boolean isNewBook = true;
		final String viewName = "book-form";
		
//		status.setComplete(); //remove previous book data from session
		
		if(book.getPublisherId() == null && book.getPublisher() != null) {
			book.setPublisherId(book.getPublisher().getId());
		}

		if(book.getFormatId() == null && book.getFormat() != null) {
			book.setFormatId(book.getFormat().getId());
		}

		if ("edit".equals(mode)) {
			Book found = bookService.getById(bookId);
			model.addAttribute("book", found);
			bookAuthors.clear();
			bookAuthors.addAll(found.getAuthors());
			title = "Edit Book";
			isNewBook = false;
		}

		session.setAttribute("isAddingAuthor", false); // Add author form not shown by default
		session.setAttribute("isNewBook", isNewBook);
		session.setAttribute("title", title);

		model.addAttribute("path", viewName);

		return viewName;
	}
	
    @PostMapping(path = "/book-form", params = "removeAuthor")
    public String removeAuthor(@RequestParam("removeAuthor") int removeId, 
			@ModelAttribute("bookAuthors") List<Author> bookAuthors){
    	int index = -1;
    	for(int i = 0;  i < bookAuthors.size(); i++) {
    		if(bookAuthors.get(i).getId() == removeId) {
    			index = i;
    			break;
    		}	
    	}
    	
    	if(index >= 0)	{
    		log.info("Removing  author: " + bookAuthors.get(index));
    		bookAuthors.remove(index);
    	}
    	return "book-form";
    }


	@PostMapping(path = "/book-form", params = "startAddAuthor")
	public String startAddingAuthor(@ModelAttribute("book") Book book,
			@ModelAttribute("bookAuthors") List<Author> bookAuthors,
			@ModelAttribute("selectedAuthor") AuthorSelection selectedAuthor,
			@ModelAttribute("newAuthor") Author newAuthor, 
			HttpSession session, Model model) {
		log.info("Start adding author.");
		session.setAttribute("isAddingAuthor", true);
		return "book-form";
	}

	@PostMapping(path = "/book-form", params = "cancelAuthor")
	public String cancelAddingAuthor(@ModelAttribute("book") Book book,
			@ModelAttribute("selectedAuthor") AuthorSelection selectedAuthor,
			@ModelAttribute("newAuthor") Author newAuthor, 
			@ModelAttribute("bookAuthors") List<Author> bookAuthors,
			HttpSession session, Model model) {
		log.info("Cancel adding author.");
		session.setAttribute("isAddingAuthor", false);
		return "book-form";
	}
	
	@PostMapping(path = "/book-form", params = "chooseAuthor")
	public String chooseAuthor(@ModelAttribute("book") Book book,
			@ModelAttribute("selectedAuthor") AuthorSelection selectedAuthor,
			@ModelAttribute("newAuthor") Author newAuthor, 
			@ModelAttribute("bookAuthors") List<Author> bookAuthors,
			HttpSession session, Model model) {
		Author author = authorService.getById(selectedAuthor.getAuthorId());
		log.info("Author choosen: " + selectedAuthor);
		book.getAuthors().add(author);
		bookAuthors.add(author);
		session.setAttribute("isAddingAuthor", false);
		return "book-form";
	}
	
	@PostMapping(path = "/book-form", params = "addAuthor")
	public String addAuthor(@ModelAttribute("book") Book book,
			@ModelAttribute("selectedAuthor") AuthorSelection selectedAuthor,
			@Valid @ModelAttribute("newAuthor") Author newAuthor, 
			@ModelAttribute("bookAuthors") List<Author> bookAuthors,
			HttpSession session, Model model) throws EntityExistsException {
		log.info("Adding new author: " + newAuthor);
		authorService.create(newAuthor);
		book.getAuthors().add(newAuthor);
		bookAuthors.add(newAuthor);
		session.setAttribute("isAddingAuthor", false);
		return "book-form";
	}
	
	
	@PostMapping("/book-form")
	public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult bookErrors,
			@Valid @ModelAttribute("selectedAuthor") AuthorSelection selectedAuthor,
			@Valid @ModelAttribute("newAuthor") Author newAuthor, BindingResult authorErrors, 
			@ModelAttribute("bookAuthors") List<Author> bookAuthors,
			SessionStatus status,
//                             @RequestParam(name = "cancel", required = false) String cancelBtn,
//                             @RequestParam("file") MultipartFile file,
			Model model) throws EntityExistsException {
//        if(cancelBtn != null) return "redirect:/books";
		
		if (bookErrors.hasErrors()) {
			List<String> errorMessages = bookErrors.getAllErrors().stream().map(err -> {
				try {
					ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
					return String.format("Error in '%s' - invalid value: '%s'", cv.getPropertyPath(), cv.getInvalidValue());
				} catch (Exception ex) {
					return String.format("Error: %s", err.toString());
				}
			}).collect(Collectors.toList());
			model.addAttribute("myErrors", errorMessages);
			return "book-form";
		} else {
			log.info("POST Book: " + book);
			if(book.getPublisherId() == null) {
				List<String> errorMessages = new ArrayList<>();
				errorMessages.add("Publisher should be chosen.");
				model.addAttribute("myErrors", errorMessages);
			}
			book.setPublisher(publisherService.getById(book.getPublisherId()));

			if(book.getFormatId() == null) {
				List<String> errorMessages = new ArrayList<>();
				errorMessages.add("Fromat should be chosen.");
				model.addAttribute("myErrors", errorMessages);
			}
			book.setFormat(formatService.getById(book.getFormatId()));
			
			if(bookAuthors == null) {
				List<String> errorMessages = new ArrayList<>();
				errorMessages.add("Authors are required.");
				model.addAttribute("myErrors", errorMessages);
			}
			book.setAuthors(bookAuthors);

			if (book.getId() == 0) {
				log.info("ADD New Book: " + book);
				bookService.create(book);
			} else {
				log.info("UPDATE Book: " + book);
				bookService.update(book);
			}
			status.setComplete();
			return "redirect:/books";
		}
	}

}

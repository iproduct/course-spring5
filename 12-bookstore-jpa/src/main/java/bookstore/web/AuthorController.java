package bookstore.web;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import bookstore.exception.EntityExistsException;
import bookstore.model.Author;
import bookstore.service.AuthorService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("authors")
@Slf4j
public class AuthorController {
	@Autowired
	private AuthorService authorService;
	
	@GetMapping
	private String getAuthorsList(Model model) {
		model.addAttribute("authors", authorService.getAll());
		return "authors-list";
	}
	
    @PostMapping(params = "edit")
    public String editAuthor(@RequestParam("edit") int editId, Model model, UriComponentsBuilder uriBuilder){
        log.info("Editing author: " + editId);
        URI uri = uriBuilder.path("/authors/author-form").query("mode=edit&authorId={id}").buildAndExpand(editId).toUri();
        return "redirect:" + uri.toString();
    }

    @PostMapping(params = "delete")
    public String deleteAuthor(@RequestParam("delete") int deleteId, Model model){
        log.info("Deleting author: " + deleteId);
        authorService.delete(deleteId);
        return "redirect:/authors";
    }

    @GetMapping("/author-form")
    public String getAuthorForm(@ModelAttribute ("author") Author author, ModelMap model,
                                @RequestParam(value="mode", required=false) String mode,
                                @RequestParam(value="authorId", required=false) Integer authorId){
        String title = "Add New Author";
        final String viewName = "author-form";
        if("edit".equals(mode)) {
             Author found = authorService.getById(authorId);
             model.addAttribute("author", found);
             title = "Edit Author";
        }
        
        model.addAttribute("path", viewName);
        model.addAttribute("title", title);
        return  viewName;
    }

    @PostMapping("/author-form")
    public String addAuthor(@Valid @ModelAttribute ("author") Author author,
                             BindingResult errors,
//                             @RequestParam(name = "cancel", required = false) String cancelBtn,
//                             @RequestParam("file") MultipartFile file,
                             Model model) throws EntityExistsException {
//        if(cancelBtn != null) return "redirect:/authors";
        if(errors.hasErrors()) {
            List<String> errorMessages = errors.getAllErrors().stream().map(err -> {
                ConstraintViolation<Author> cv = err.unwrap(ConstraintViolation.class);
                return String.format("Error in '%s' - invalid value: '%s'", cv.getPropertyPath(), cv.getInvalidValue());
            }).collect(Collectors.toList());
            model.addAttribute("myErrors", errorMessages);
            return "author-form";
        } else {
            log.info("POST Author: " + author);
            if (author.getId() == 0) {
                log.info("ADD New Author: " + author);
                authorService.create(author);
            } else {
                log.info("UPDATE Author: " + author);
                authorService.update(author);
            }
            return "redirect:/authors";
        }
    }

	
}

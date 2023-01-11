package course.spring.blogs.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import course.spring.blogs.entity.User;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static course.spring.blogs.utils.ErrorHandlingUtils.handleValidationErrors;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id:\\d+}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(@Valid @RequestBody User user, Errors errors) {
        handleValidationErrors(errors);

        var created = userService.create(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(created);
    }



    @PutMapping("/{id}")
    public User updateAticle(@PathVariable("id") Long id, @Valid @RequestBody User user, Errors errors) {
        handleValidationErrors(errors);

        if(!id.equals(user.getId())) {
            throw new InvalidEntityDataException(
                    String.format("ID in URL='%d' is different from ID in message body = '%d'", id, user.getId()));
        }
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public User deleteUserById(@PathVariable("id") Long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping(value = "/count", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getArticlsCount() {
        return Long.toString(userService.getUsersCount());
    }



}

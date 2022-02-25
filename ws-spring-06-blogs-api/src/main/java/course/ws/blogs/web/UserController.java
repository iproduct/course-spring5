package course.ws.blogs.web;

import course.ws.blogs.entity.User;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.UserService;
import course.ws.blogs.util.ValidationErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static course.ws.blogs.util.ValidationErrorUtil.handleValidationErrors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id:[\\d]+}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping(params = "username")
    public User getUserByUsername(@RequestParam("username") String username) {
        return userService.getByUsername(username);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user, Errors errors) {
        handleValidationErrors(errors);
        User created = userService.create(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    public User updateUser(@Valid @RequestBody User user, Errors errors, @PathVariable("id") Long id) {
        if (!id.equals(user.getId())) {
            throw new InvalidEntityDataException(
                    String.format("ID in URI: '%d' id different from ID in body: '%d'", id, user.getId()));
        }
        handleValidationErrors(errors);
        return userService.update(user);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public User deleteUser(@PathVariable("id") Long id) {
        return userService.deleteById(id);
    }

}

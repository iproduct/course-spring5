package course.spring.restmvc.web;

import course.spring.restmvc.entity.User;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static course.spring.restmvc.util.ErrorHandlingUtil.handleErrors;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user, Errors errors, Principal principal) {
        handleErrors("Validation errors creating user", errors);
        User created = userService.addUser(user);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(created);
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user, Errors errors) {
        handleErrors("Validation errors updating user", errors);
        User found = userService.getUserById(id);
        if(!found.getId().equals(user.getId())) {         // or simpler alternative - use ResponseStatusException
           throw new InvalidEntityDataException(
                    String.format("User ID:%d is different from resource URL ID: %d.", user.getId(), id));
        }
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable("id") Long id) {
         return userService.deleteUser(id);
    }


}

package course.ws.blogs.web;

import course.ws.blogs.entity.User;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static course.ws.blogs.util.ErrorHandlingUtils.checkErrors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User>  getAllUsers() {
       return userService.findAllUsers();
    }

    @GetMapping("/{id:\\d+}")
    public User  getUserById(@PathVariable Long id) {
       return userService.findUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(@Valid @RequestBody User user, Errors errors) {
        checkErrors(errors);
        User newUser = userService.create(user);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                                .buildAndExpand(newUser.getId()).toUri())
                .body(newUser);
    }



    @PutMapping("/{id:\\d+}")
    public User updateUser(@Valid @RequestBody User user, Errors errors, @PathVariable Long id) {
        if(!id.equals(user.getId())){
            throw new InvalidEntityDataException(
                    String.format("ID='%s' in path differs from ID='%s' in message body", id, user.getId()));
        }
        checkErrors(errors);
        return userService.update(user);
    }

    @DeleteMapping("/{id:\\d+}")
    public User  deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

}

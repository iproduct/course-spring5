package course.spring.intro.web;

import course.spring.intro.exception.InvalidEntityDataException;
import course.spring.intro.model.User;
import course.spring.intro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id:^[A-Fa-f0-9]{24}$}")
    public User getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, Errors errors) {
        if(errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid User data: ",
                    errors.getAllErrors().stream()
                            .map(err -> err.getDefaultMessage())
                            .collect(Collectors.toList()));
        }
        User created = userService.createUser(user);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri()
        ).body(created);
    }

    @PutMapping("/{id:^[A-Fa-f0-9]{24}$}")
    public User updateUser( @PathVariable("id") String id,
                            @Valid @RequestBody User user, Errors errors) {
        if(errors.hasErrors()) {
            List<String> globalErr = errors.getGlobalErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            List<String> filedErr = errors.getFieldErrors().stream()
                    .map(err -> String.format("%s='%s': %s", err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            List<String> allErr = new ArrayList<>();
            allErr.addAll(globalErr);
            allErr.addAll(filedErr);
            throw new InvalidEntityDataException("Invalid User data: ", allErr);
        }
        if(!id.equals(user.getId())) {
            throw new InvalidEntityDataException("User ID:%s in the URL differs from ID:%s in the body.");
        }
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id:^[A-Fa-f0-9]{24}$}")
    public User deleteUser(@PathVariable("id") String id) {
        return userService.deleteUserById(id);
    }

}

package course.spring.restjpa.web;

import course.spring.restjpa.exception.InvalidEntityDataException;
import course.spring.restjpa.entity.User;
import course.spring.restjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll(){
        return  userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id){
        return  userService.findById(id);
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> findByTitle(@Valid @RequestBody User user, Errors errors){
        if (errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid user data",
                    errors.getAllErrors().stream().map(e -> e.toString()).collect(Collectors.toList()));
        }
        User created = userService.create(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId())
                ).body(created);
    }
}

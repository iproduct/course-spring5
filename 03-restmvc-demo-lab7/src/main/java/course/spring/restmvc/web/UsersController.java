package course.spring.restmvc.web;

import course.spring.restmvc.domain.UsersService;
import course.spring.restmvc.exception.InvalidEntityException;
import course.spring.restmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<User> getUsers(){
        return usersService.findAll();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") String userId) {
        return usersService.findById(userId);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()) {
            String  message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr );
            throw new InvalidEntityException(message);
        }
        User created = usersService.add(user);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
                .body(created);
    }

    @PutMapping("{id}")
    public User update(@PathVariable String id, @Valid @RequestBody User user) {
        if(!id.equals(user.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", user.getId(), id));
        }
        return usersService.update(user);
    }

    @DeleteMapping("{id}")
    public User remove(@PathVariable String id) {
        return usersService.remove(id);
    }

}

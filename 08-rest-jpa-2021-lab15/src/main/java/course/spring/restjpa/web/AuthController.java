package course.spring.restjpa.web;

import course.spring.restjpa.exception.InvalidEntityDataException;
import course.spring.restjpa.entity.User;
import course.spring.restjpa.model.Credentials;
import course.spring.restjpa.model.LoginResponse;
import course.spring.restjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<User> findByTitle(@Valid @RequestBody User user, Errors errors){
        if (errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid user data",
                    errors.getAllErrors().stream().map(e -> e.toString()).collect(Collectors.toList()));
        }
        if(!user.getRoles().equals("READER")){
            throw new InvalidEntityDataException(
                String.format("Role: '%s' is invalid.You can self-register only in 'READER' role", user.getRoles()));
        }
        User created = userService.create(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PostMapping("login")
    public LoginResponse login(@Valid @RequestBody Credentials credentials, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid user data",
                    errors.getAllErrors().stream().map(e -> e.toString()).collect(Collectors.toList()));
        }
        return null;
    }
}

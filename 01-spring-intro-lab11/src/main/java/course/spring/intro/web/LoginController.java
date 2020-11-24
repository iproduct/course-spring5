package course.spring.intro.web;

import course.spring.intro.exception.InvalidEntityDataException;
import course.spring.intro.model.Credentials;
import course.spring.intro.model.JwtResponse;
import course.spring.intro.model.Role;
import course.spring.intro.model.User;
import course.spring.intro.service.UserService;
import course.spring.intro.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/api/login")
    public JwtResponse login(@Valid @RequestBody Credentials credentials, Errors errors){
        if(errors.hasErrors()){
            new InvalidEntityDataException("Invalid login data.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        final User user = userService.getUserByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        log.info("Login succesful for {}: {}", user, token); //remove it!
        return new JwtResponse(user, token);
    }

    @PostMapping("/api/register")
    public User register(@Valid @RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            new InvalidEntityDataException("Invalid user data",
                    errors.getFieldErrors().stream().map(fieldErr ->
                            fieldErr.getField() + " = " + fieldErr.getRejectedValue() + ": " + fieldErr.getDefaultMessage())
                    .collect(Collectors.toList())
                    );
        }
        user.setRoles(Set.of(Role.READER));
        return userService.createUser(user);
    }


}

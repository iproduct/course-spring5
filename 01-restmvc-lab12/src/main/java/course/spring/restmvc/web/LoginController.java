package course.spring.restmvc.web;

import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.model.Credentials;
import course.spring.restmvc.model.JwtResponse;
import course.spring.restmvc.model.Role;
import course.spring.restmvc.model.User;
import course.spring.restmvc.service.UserService;
import course.spring.restmvc.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.restmvc.util.ErrorHandlingUtils.getViolationsAsStringList;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public JwtResponse login(@Valid @RequestBody Credentials credentials, Errors errors) {
        if(errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid username or password");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()));
        final User user = userService.getUserByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        log.info("Login successfull for {}: {}", user.getUsername(), token); //remove it!
        return new JwtResponse(user, token);
    }

    @PostMapping("/api/register")
    public User register(@Valid @RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            new InvalidEntityDataException("Invalid user data",  getViolationsAsStringList(errors));
        }
        user.setRoles(Set.of(Role.READER));
        return userService.addUser(user);
    }
}

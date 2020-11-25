package course.spring.restmvc.web;

import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.model.Credentials;
import course.spring.restmvc.model.JwtResponse;
import course.spring.restmvc.model.User;
import course.spring.restmvc.service.UserService;
import course.spring.restmvc.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
        final User user = userService.getUserByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        log.info("Login successfull for {}: {}", user.getUsername(), token); //remove it!
        return new JwtResponse(user, token);
    }
}

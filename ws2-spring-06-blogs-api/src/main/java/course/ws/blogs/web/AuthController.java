package course.ws.blogs.web;

import course.ws.blogs.entity.Role;
import course.ws.blogs.entity.User;
import course.ws.blogs.service.UserService;
import course.ws.blogs.web.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("register")
    public ResponseEntity<User> findByTitle(@Valid @RequestBody User userDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidClientDataException("Invalid user data",
                    errors.getAllErrors().stream().map(e -> e.toString()).collect(Collectors.toList()));
        }
        if (!userDto.getRoles().equals(Set.of(Role.READER))) {
            throw new InvalidClientDataException(
                    String.format("Role: '%s' is invalid. You can self-register only in 'READER' role.",
                            userDto.getRoles()));
        }
        User created = userService.create(mapper.map(userDto, User.class));
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PostMapping("login")
    public LoginResponse login(@Valid @RequestBody Credentials credentials, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidClientDataException("Invalid user data",
                    errors.getAllErrors().stream().map(e -> e.toString()).collect(Collectors.toList()));
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()
        ));
        final User user = userService.findByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        return new LoginResponse(user, token);
    }
}













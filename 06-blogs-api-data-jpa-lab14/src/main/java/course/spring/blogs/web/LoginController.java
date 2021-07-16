package course.spring.blogs.web;


import course.spring.blogs.dto.Credentials;
import course.spring.blogs.dto.JwtResponse;
import course.spring.blogs.entity.User;
import course.spring.blogs.service.UserService;
import course.spring.blogs.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
//    @Autowired
//    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody Credentials credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()
        ));
        final User user = userService.getUserByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        log.info("Login successful for {}: {}", user, token); // don't do it in production code!
        return ResponseEntity.ok(new JwtResponse(user, token));
    }
}

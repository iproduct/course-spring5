package course.spring.restmvc.web;

import course.spring.restmvc.dto.Credentials;
import course.spring.restmvc.dto.JwtResponse;
import course.spring.restmvc.dto.PostUserDto;
import course.spring.restmvc.entity.User;
import course.spring.restmvc.service.UserService;
import course.spring.restmvc.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody Credentials credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()
        ));
        final User user = userService.getUserByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        log.info("Login successful for {}: {}", user, token);
        return ResponseEntity.ok(new JwtResponse(mapper.map(user, PostUserDto.class), token));
    }
}

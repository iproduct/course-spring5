package course.spring.restmvc.model;

import lombok.Data;

@Data
public class JwtResponse {
    private final User user;
    private final String token;
}

package course.spring.blogs.dto;

import course.spring.blogs.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class JwtResponse {
    private User user;
    private String token;
}

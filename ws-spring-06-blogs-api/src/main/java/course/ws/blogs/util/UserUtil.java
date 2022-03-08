package course.ws.blogs.util;

import course.ws.blogs.entity.User;
import course.ws.blogs.exception.UnauthorizedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public class UserUtil {
    public static User getUser(Principal authentication) {
        if(!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new UnauthorizedException("Error: User should be authenticated");
        }
        var token = (UsernamePasswordAuthenticationToken) authentication;
        if(token.getPrincipal() == null || !(token.getPrincipal() instanceof User)) {
            throw new UnauthorizedException("Error: User should be authenticated");
        }
        var user = (User) token.getPrincipal();
        return user;
    }
}

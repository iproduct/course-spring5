package course.ws.blogs.util;

import course.ws.blogs.entity.User;
import course.ws.blogs.exception.UnautorizedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public class UserUtils {
    public static User getUser(Principal principal) {
        if(principal == null || !(principal instanceof UsernamePasswordAuthenticationToken)) {
            throw new UnautorizedException("Unauthorised. No authenticated user.");
        }
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        if(!(token.getPrincipal() instanceof User || (User)token.getPrincipal() == null)) {
            throw new UnautorizedException("Unauthorised. No authenticated user.");
        }
        User user = (User)token.getPrincipal();
        return user;
    }
}

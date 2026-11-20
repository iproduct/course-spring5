package course.spring.blog.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class AuthController {
    // Login form
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}

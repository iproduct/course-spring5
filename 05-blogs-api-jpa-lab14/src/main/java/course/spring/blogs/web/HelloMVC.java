package course.spring.blogs.web;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;


@RestController
@RequestMapping("/hello")
public class HelloMVC {

    @GetMapping(path = {"", "/{name}"}, produces = TEXT_PLAIN_VALUE)
    public String sayHelloByName(@PathVariable(required = false) String name) {
        return String.format("Hello %s from Spring MVC!", name == null ? "user" : name);
    }

    @GetMapping("/users")
    public String welcomeUser(
            @RequestParam(value = "username", required = false) String username,
            @RequestHeader("Accept") String accept,
            @CookieValue(value = "JSESSIONID", required = false) String sessionId,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        StringBuilder sb = new StringBuilder(
                String.format("<h2>Welcome, %s!</h2>", username == null ? "User" : username));

        // Get headers
        sb.append("<h3>Request Headers:</h3><ul>");
        sb.append("<li>Accept: ").append(accept).append("</li><hr>");
        request.getHeaderNames().asIterator().forEachRemaining(hname ->{
            sb.append("<li>").append(hname).append(": ").append(request.getHeader(hname)).append("</li>");
        });
        sb.append("</ul>");

        // Get cookies
        sb.append("<hr><h3>Request cookies:</h3><ul>");
        if(request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                sb.append("<li>").append(c.getName()).append(": ").append(c.getValue()).append("</li>");
            }
        }
        sb.append("</ul>");
        session.invalidate();
        response.addCookie(new Cookie("MyCustomCookie", "MyCustomValue"));
        return sb.toString();
    }
}

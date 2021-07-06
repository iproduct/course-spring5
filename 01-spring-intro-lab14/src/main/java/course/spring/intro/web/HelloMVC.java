package course.spring.intro.web;

import org.springframework.web.bind.annotation.*;

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
            @CookieValue(value = "JSESSIONID", required = false) String sessionId
    ) {
        StringBuilder sb = new StringBuilder(
                String.format("<h2>Welcome, %s!</h2>", username == null ? "User" : username));

        // Get headers
        sb.append("<h3>Request Headers:</h3><ul>");
        sb.append("<li>Accept: ").append(accept).append("</li>");
        sb.append("</ul>");

        // Get cookies
        sb.append("<br><h3>Request cookies:</h3><ul>");
        sb.append("<li>JSESSIONID: ").append(sessionId).append("</li>");
        sb.append("</ul>");
        return sb.toString();
    }
}

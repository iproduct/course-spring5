package course.spring.intro.web;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class HelloMVC {

    @GetMapping("/hello")
    public String sayHello(
            @RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
        return String.format("Hello %s, from Spring 5!", name);
    }

    @GetMapping({"/hello/{name}"})
    public String sayHelloPath(
            @PathVariable(name = "name", required = false) String name) {
        return String.format("Hello %s, from Spring 5!", name);
    }

    @GetMapping({"/hello-cookie"})
    public String sayHelloCookie(
            @CookieValue(name = "JSESSIONID", required = false) String jsessionid,
            HttpSession session) {
        session.invalidate();
        return String.format("Your JSESSIONID is: %s", jsessionid);
    }

    @GetMapping("/hello-header")
    public String sayHelloAcceptHeader(
            @RequestHeader("accept") String acceptHeader
    ) {
        return String.format("ACCEPT header is: %s", acceptHeader);
    }

}

package course.spring.restmvc.web;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class HelloMVC {
    @GetMapping("/hello")
    public String sayHello(
            @RequestParam(name="name", required = false, defaultValue = "Stranger") String name) {
        return String.format("Hello %s, from Spring MVC!", name);
    }

    @GetMapping("/hello/{name}")
    public String sayHelloPath(@PathVariable("name") String name) {
        return String.format("Hello %s, from Spring MVC!", name);
    }

    @GetMapping("/hello-cookie")
    public String sayHelloCookie(
            @CookieValue(name = "JSESSIONID", required = false) String jsessionid,
            HttpSession session) {
        session.invalidate();
        return String.format("JSESSIONID: %s", jsessionid);
    }

    @GetMapping("/hello-header")
    public String sayHelloHeader(
            @RequestHeader(name = "Accept", required = false) String accept) {
        return String.format("Accept: %s", accept);
    }


}

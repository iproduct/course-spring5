package course.spring.intro.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloMVC {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Spring 5!";
    }
}

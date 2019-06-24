package course.spring.webfluxdemo.web;

import org.springframework.web.bind.annotation.*;

import javax.swing.*;

@RestController
@RequestMapping("/hello")
public class HelloRestController {

    @GetMapping("{name}")
    public String sayHello(
        @PathVariable("name") String name,
        @RequestParam(value = "role", defaultValue = "User") String role){
        return "<h2>Hello, " + name  + " [" + role + "], "
                + " from Spring 5!</h2>";
    }
}

package course.spring.intro.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloRestContorller {
    @GetMapping
    public String hello(@RequestParam(value="name", required=false) String name) {
        return "Hello " + (name != null && name.length() > 0 ? name : "Anonymous")  + ", from Java World!";
    }
}

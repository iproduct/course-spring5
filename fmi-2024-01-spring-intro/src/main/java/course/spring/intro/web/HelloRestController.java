package course.spring.intro.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloRestController {
    @GetMapping(path = {"","/{name}"})
    public String sayHello(@PathVariable(name = "name", required = false) String name){
        return "Hello " + name + ", from my REST Endpoint!";
    }
}

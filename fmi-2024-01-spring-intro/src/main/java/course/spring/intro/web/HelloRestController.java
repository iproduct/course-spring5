package course.spring.intro.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloRestController {
    @GetMapping(path = {"","/{name}"})
    public String sayHello(@PathVariable(name = "name", required = false) String name,
                           @RequestParam(value = "title", required = false) String title){
        return "Hello "+ title + " " + name + ", from your REST Endpoint!";
    }
}

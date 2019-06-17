package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloSpringController {

    @GetMapping("/hello/{id}")
    public String sayHallo(@PathVariable("id") String id,
            @MatrixVariable(name = "name", pathVar = "id", defaultValue = "Anonymous") String name) {
        return String.format("Hello %s:%s from Spring!", id, name);
    }

}

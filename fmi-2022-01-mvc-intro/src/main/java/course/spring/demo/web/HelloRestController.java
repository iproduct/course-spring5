package course.spring.demo.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloRestController {
    @GetMapping("/query")
    public String sayHelloQueryParam(@RequestParam(name="name", required=false) String name){
        return String.format("QUERY PARAM: Hello %s, from Spring 5!", name== null ? "Anonimous": name);
    }

    // Sample query: http://localhost:8080/api/hello/query?name=Trayan
    @GetMapping({"/", "/{name}"})
    public String sayHello(@PathVariable(name="name", required=false) String name){
        return String.format("Hello, %s from Spring 5!", name== null ? "Anonimous": name);
    }

}

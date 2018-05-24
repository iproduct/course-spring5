package org.iproduct.spring.webfluxdemo.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name){
        return String.format("Hello, %s from Spring Boot!", name);
    }
}

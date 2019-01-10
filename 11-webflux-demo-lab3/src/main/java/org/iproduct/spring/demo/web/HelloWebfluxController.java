package org.iproduct.spring.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWebfluxController {

    @GetMapping("/hello")
    String sayHello(@RequestParam(defaultValue="Anonimous") String name) {
        return "Hello " + name;
    }
}

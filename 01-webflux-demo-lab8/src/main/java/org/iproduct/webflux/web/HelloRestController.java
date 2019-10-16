package org.iproduct.webflux.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloRestController {
    @GetMapping
    public String sayHello() {
        return "Hello from WebFlux!";
    }
}

package org.iproduct.spring.webflux.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWebfluxController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from WebFlux!!!";
    }
}

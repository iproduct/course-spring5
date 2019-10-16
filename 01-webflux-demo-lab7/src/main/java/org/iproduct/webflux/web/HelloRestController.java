package org.iproduct.webflux.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloRestController {
    @GetMapping("{name}")
    public String sayHello(@PathVariable("name") String name) {
        return "Hello " + name + " from Spring Webflux!";
    }
}

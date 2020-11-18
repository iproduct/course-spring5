package org.iproduct.spring.webmvc.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping(produces = "text/plain")
    public String sayHello(){
        return "Hello from Spring";
    }
}

package org.iproduct.spring.webfluxdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringController {
    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello!!!";
    }
}

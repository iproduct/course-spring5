package org.iproduct.spring.registration.web;

import org.iproduct.spring.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    ModelAndView getUsers() {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }
}

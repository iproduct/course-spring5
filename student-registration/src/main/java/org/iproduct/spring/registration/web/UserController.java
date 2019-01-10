package org.iproduct.spring.registration.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.registration.model.User;
import org.iproduct.spring.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    ModelAndView getUsers() {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }

    @GetMapping(value = "/add")
    String getUserForm (@ModelAttribute("user") User user) {
        return"user-form";
    }

    @PostMapping(value = "/add")
    public String addArticle(@Valid @ModelAttribute("user") User user, BindingResult result,
             @RequestParam("confirmPassword") String confirmPassword,
             ModelMap model) {

        log.info("New User submitted: " + user);

        boolean success = true;

        if(result.hasErrors()) {
            log.debug("User validation errors: {}", result.getAllErrors());
            success = false;
        }
        if(!user.getPassword().equals(confirmPassword)){
            log.debug("Error: Passwords do not match", user.getPassword(), confirmPassword);
            model.addAttribute("errorConfirmPassword", true);
            success = false;
        }

        if( success) {
            userService.addUser(user);
            return "redirect:/users";
        } else {
            return "user-form";
        }

//        if(!file.isEmpty() && file.getOriginalFilename().length() > 0) {
//            handleMultipartFile(file);
//            article.setPictureUrl(file.getOriginalFilename());
//        }
//        repository.create(article);
    }
}

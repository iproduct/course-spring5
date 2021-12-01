package demos.spring.vehicles.web;

import demos.spring.vehicles.model.Offer;
import demos.spring.vehicles.model.User;
import demos.spring.vehicles.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String getOfferForm(Model model, HttpServletRequest request) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "auth-register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute("user") User user, final BindingResult binding, RedirectAttributes redirectAttributes) {
        if (binding.hasErrors()) {
            log.error("Error registering user: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:register";
        }
        try {
            authService.register(user);
        } catch (Exception ex) {
            log.error("Error registering user", ex);
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:register";
        }

        return "redirect:login";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", "");
        }
        if (!model.containsAttribute("password")) {
            model.addAttribute("password", "");
        }

        return "auth-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @ModelAttribute("redirectUrl") String redirectUrl,
                        RedirectAttributes redirectAttributes, HttpSession session) {
        User loggedUser = authService.login(username, password);
        if (loggedUser == null) {
            String errors = "Invalid username or password.";
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:login";
        } else {
            session.setAttribute("user", loggedUser);
            if(redirectUrl != null && redirectUrl.length() > 0 ) {
                return "redirect:" + redirectUrl;
            } else {
                return "redirect:/";
            }
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}

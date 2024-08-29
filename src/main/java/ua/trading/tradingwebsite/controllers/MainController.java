package ua.trading.tradingwebsite.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.trading.tradingwebsite.models.User;
import ua.trading.tradingwebsite.services.UserService;

@Controller
@AllArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("message", "Welcome");
        return "welcome";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

        if (userService.userExists(user.getEmail())) {
            model.addAttribute("UserExistsError", "User already exists");
            return "registration";
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("confirmPasswordError", "Passwords do not match");
            return "registration";
        }

        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model) {
        return "login";
    }


    @GetMapping("/pricing")
    public String price() {
        return "pricing";
    }
}
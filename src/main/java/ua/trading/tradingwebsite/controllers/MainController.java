package ua.trading.tradingwebsite.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String registration(@ModelAttribute("user") User user, Model model) {

//        if (!userService.createUser(user)) {
//            model.addAttribute("message", "User creation failed");
//            return "registration";
//        }

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
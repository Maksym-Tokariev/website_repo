package ua.trading.tradingwebsite.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.trading.tradingwebsite.models.User;
import ua.trading.tradingwebsite.services.MailSenderService;
import ua.trading.tradingwebsite.services.UserService;

@Controller
@AllArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/home")
    public String home(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", new User());
        return "home";
    }

    @PostMapping("/home")
    public String home(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if (userService.userExists(user.getEmail())) {
            model.addAttribute("error", "User already exists");
            return "home";
        }

        if (bindingResult.hasErrors()) {
            return "home";
        }

        userService.createUser(user);
        return "redirect:/pricing";
    }

    @GetMapping("/pricing")
    public String price() {
        return "pricing";
    }
}
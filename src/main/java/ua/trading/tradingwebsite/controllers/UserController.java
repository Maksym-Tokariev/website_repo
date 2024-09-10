package ua.trading.tradingwebsite.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.trading.tradingwebsite.models.User;
import ua.trading.tradingwebsite.repository.UserRepository;

@Controller
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;

    @GetMapping("/user")
    public String user(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", user);
        return "userList";
    }
}

package ua.trading.tradingwebsite.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.trading.tradingwebsite.models.User;
import ua.trading.tradingwebsite.services.UserService;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/user/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String user(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }
}

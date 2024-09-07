package ua.trading.tradingwebsite.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.trading.tradingwebsite.models.User;
import ua.trading.tradingwebsite.services.MailSenderService;
import ua.trading.tradingwebsite.services.UserService;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;

    private MailSenderService mailSenderService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model
    ) {

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

        mailSenderService.sendMail(
                "paranoid063@gmail.com",
                "Registration confirm",
                "Hi"
        );

        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateCode(code);

        if (isActivated)
            model.addAttribute("message", "User successfully activated");
        else
            model.addAttribute("message", "Activation failed");

        return "login";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model) {
        return "login";
    }

}

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
            bindingResult.getFieldError();
            return "registration";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("confirmPasswordError", "Passwords do not match");
            return "registration";
        }

        userService.createUser(user);

        return "redirect:/activate";
    }

    @GetMapping("/activate/{code}")
    public String activate(User user, Model model, @PathVariable String code) {
        boolean isActivated = userService.activateCode(code);

        if (isActivated) {
            model.addAttribute("messageSuccess", "User successfully activated");
            mailSenderService.sendMail(
                    user.getEmail(),
                    "Registration confirmed",
                    user.getName() + " was registered."
            );
        }
        else
            model.addAttribute("messageFail", "Activation failed");

        return "login";
    }

    @GetMapping("/activate")
    public String welcome(Model model) {
        model.addAttribute("message", "Check your email address to activate your account");
        return "activation";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model) {
        return "login";
    }

}

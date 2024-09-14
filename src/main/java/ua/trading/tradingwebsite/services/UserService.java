package ua.trading.tradingwebsite.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ua.trading.tradingwebsite.models.Role;
import ua.trading.tradingwebsite.models.User;
import ua.trading.tradingwebsite.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    private MailSenderService mailSenderService;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public void createUser(User user) {

        user.setPassword(encoder().encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        user.getRole().add(Role.USER);
        userRepository.save(user);
        log.info("Saved User with email: {}", user.getEmail());

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, " + user.getName() + " \n" +
                            "Visit next link: http://localhost:8083/activate/%s",
                    user.getEmail(), user.getActivationCode()
            );
            mailSenderService.sendMail(user.getEmail(), "Activation Code", message);
        }

    }

    public boolean userExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }

    public boolean activateCode(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null)
            return false;
        else {

            user.setActivationCode(null);
            user.setActive(true);

            userRepository.save(user);

            return true;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

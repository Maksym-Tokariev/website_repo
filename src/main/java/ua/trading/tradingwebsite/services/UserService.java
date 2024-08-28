package ua.trading.tradingwebsite.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.trading.tradingwebsite.models.User;
import ua.trading.tradingwebsite.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public void createUser(User user) {

        user.setPassword(encoder().encode(user.getPassword()));
        userRepository.save(user);

    }

    public boolean userExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }
}

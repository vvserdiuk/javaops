package ru.javaops.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.LoggedUser;
import ru.javaops.model.User;
import ru.javaops.repository.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Service("userDetailsService")
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public LoggedUser loadUserByUsername(final String email) {
        String lowercaseLogin = email.toLowerCase();
        log.debug("Authenticating {}", email);
        User user = userRepository.findByEmail(lowercaseLogin);
        if (user == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }
        if (!user.isActive()) {
            throw new DisabledException("User " + lowercaseLogin + " was not activated");
        }
        return new LoggedUser(user);
    }
}

package de.hawhamburg.logindemo.security;

import de.hawhamburg.logindemo.user.Role;
import de.hawhamburg.logindemo.user.User;
import de.hawhamburg.logindemo.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is only for demo purposes and should be deleted if user accounts
 * can be registered via the web application or by other means.
 * It will insert default users on app start into the database.
 */
@Configuration
class DefaultUsersConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    DefaultUsersConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * CommandLineRunner is executed once when the application is started and the
     * Spring context has been loaded.
     * <p>
     * !! Only for demo purposes and should not be used in real environments !!
     *
     * @see <a href="https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html"></a>
     */
    @Bean
    CommandLineRunner onApplicationStart() {
        return _ -> {                              // raw passwords must be encoded
            addUserToDatabaseOnce("user", passwordEncoder.encode("user"), "user@haw-hamburg.de", Role.USER);
            addUserToDatabaseOnce("admin", passwordEncoder.encode("admin"), "admin@haw-hamburg.de", Role.USER, Role.ADMIN);
        };
    }

    private void addUserToDatabaseOnce(String username, String password, String email, String... authorities) {
        if (doesNotExistsInDatabase(username)) {
            addUserToDatabase(username, password, email, authorities);
        }
    }

    private boolean doesNotExistsInDatabase(String username) {
        return userRepository.findByName(username).isEmpty();
    }

    private void addUserToDatabase(String username, String password, String email, String... authorities) {
        var newDefaultUser = new User(username, password, email, authorities);
        userRepository.save(newDefaultUser);
    }
}
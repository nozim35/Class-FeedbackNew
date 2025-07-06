package de.hawhamburg.classfee.security;

import de.hawhamburg.classfee.user.User;
import de.hawhamburg.classfee.user.UserRepository;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Retrieves a user from the database during the login process.
 * For this, {@link User} must implement {@link UserDetails} which is required by Spring.
 * <p>
 * FYI: {@link UserDetailsService} (Spring) is used by {@link DaoAuthenticationProvider} (Spring) to
 * retrieve a username, password, and authorities for authentication.
 *
 * @see <a href="https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html"></a>
 */
class UserRetriever implements UserDetailsService {

    private final UserRepository userRepository;

    UserRetriever(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Our user entity is queried from the database.
     * The {@link User} must implement {@link UserDetails} because Spring requires this interface.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("Cannot find user account: " + username));
    }
}
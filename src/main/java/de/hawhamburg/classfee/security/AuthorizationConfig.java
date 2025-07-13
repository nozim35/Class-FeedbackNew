package de.hawhamburg.classfee.security;

import de.hawhamburg.classfee.user.Role;
import de.hawhamburg.classfee.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.http.HttpMethod;


/**
 * Manages the authorization for all endpoints of this web application.
 */
@Configuration
@EnableWebSecurity
class AuthorizationConfig {

    @Bean
    SecurityFilterChain filters(HttpSecurity http) throws Exception {
        var loginEndpoint = "/login";

        http.formLogin(form -> form
                .loginPage(loginEndpoint)
                .defaultSuccessUrl("/", true) // Nach erfolgreichem Login IMMER auf "/" umleiten
                .failureHandler(new SimpleUrlAuthenticationFailureHandler(loginEndpoint + "?error=true"))
                .permitAll()
        );


        http.logout(logout -> logout.logoutSuccessUrl("/")); // Show landing page after successful logout

        http.authorizeHttpRequests(authz -> authz
            .requestMatchers("/admin").hasAuthority(Role.ADMIN) // This endpoint is only available for users with the ROLE_ADMIN.
            .requestMatchers(
                    "/secure",
                    "/feedbacks",
                    "/feedback_form",
                    "/logout",
                    "/semester",
                    "/module",
                    "/dramaturgie1",
                    "/informatik1",
                    "/mathe1",
                    "/media_game_design1",
                    "/medienrecht",
                    "/programmieren1",
                    "/sommersemester25",
                    "/wintersemester24_25"
                    ).authenticated() // This endpoint is available for any logged-in user (regardless of the role).
                .requestMatchers(HttpMethod.POST, "/feedback/new").authenticated()
                .requestMatchers("/register").anonymous() // This endpoint is available for any not-logged-in user.
            .requestMatchers(
                    "/",
                    "/styles.css",
                    "/error",
                    "/login",
                    "/about"
            ).permitAll() // Make landing page publicly accessible
            .anyRequest().denyAll() // Secure any other page (aka blacklist)

        );

        return http.build();
    }

    /**
     * Tells Spring what {@link PasswordEncoder} to use to encrypt passwords.
     *
     * @see <a href="https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html"></a>
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Tells Spring to use our custom {@link UserRetriever} that
     * retrieves a user from the database during the login process.
     * Spring requires a {@link UserDetailsService} to find stored users.
     */
    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserRetriever(userRepository);
    }

    /**
     * This custom {@link AuthenticationManager} is required for {@code AutoLogin}.
     * It tells Spring to use our custom {@link #userDetailsService} and {@link #passwordEncoder()}.
     * <p>
     * The {@link AuthenticationManager} orchestrates the authentication process by iterating
     * through a list of given {@link AuthenticationProvider}.
     * The {@link AuthenticationManager} will provide an {@link Authentication} object, if login was successful.
     */
    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }
}
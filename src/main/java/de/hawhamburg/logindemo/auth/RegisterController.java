package de.hawhamburg.logindemo.auth;

import de.hawhamburg.logindemo.user.User;
import de.hawhamburg.logindemo.user.UserRepository;
import de.hawhamburg.logindemo.util.Redirect;
import de.hawhamburg.logindemo.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static de.hawhamburg.logindemo.util.StringUtil.isEmpty;

@Controller
class RegisterController {

    private static final String REGISTER_ENDPOINT = "/register";
    private static final String REGISTER_MUSTACHE_FILE = "register";
    private static final String ERROR_MESSAGE_KEY = "errorMessage";

    private final AutoLogin autoLogin;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public RegisterController(AutoLogin autoLogin, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.autoLogin = autoLogin;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping(REGISTER_ENDPOINT)
    public String showRegisterTemplate() {
        return REGISTER_MUSTACHE_FILE;
    }

    @PostMapping(REGISTER_ENDPOINT)
    public String tryRegisterNewUser(HttpServletRequest request, HttpServletResponse response, Model model, String username, String password, String email) {
        final var usernameTrimmed = StringUtil.trim(username);
        final var emailTrimmed = StringUtil.trim(email);

        try { // with database connection
            if (isEmpty(usernameTrimmed) | isEmpty(password) | isEmpty(emailTrimmed)) {
                model.addAttribute(ERROR_MESSAGE_KEY, "Invalid input.");
                return REGISTER_MUSTACHE_FILE;
            } else if (userAlreadyExistsWith(usernameTrimmed)) {
                model.addAttribute(ERROR_MESSAGE_KEY, "Username already exists.");
                return REGISTER_MUSTACHE_FILE;
            }
            saveNewUser(usernameTrimmed, password, email);
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE_KEY, "Database not accessible");
            return REGISTER_MUSTACHE_FILE;
        }

        autoLogin.loginUser(request, response, usernameTrimmed, password);

        return Redirect.to("/");
    }

    private boolean userAlreadyExistsWith(String name) {
        return userRepository.findByName(name).isPresent();
    }

    private void saveNewUser(String username, String password, String email) {
        var newUser = new User(username, passwordEncoder.encode(password), email);
        userRepository.save(newUser);
    }
}
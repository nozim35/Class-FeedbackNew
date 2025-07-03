package de.hawhamburg.logindemo;

import de.hawhamburg.logindemo.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class MainController {

    private static final String AUTHENTICATED_KEY = "authenticated";
    private static final String USERNAME_KEY = "username";
    private static final String AUTHORITIES_KEY = "authorities";
    private static final String EMAIL_KEY = "email";

    @GetMapping("/")
    String landingPage(Model model, Authentication authentication) {
        if (authentication == null) {
            model.addAttribute(AUTHENTICATED_KEY, false);
        } else {
            model.addAttribute(AUTHENTICATED_KEY, authentication.isAuthenticated());
        }
        return "index";
    }

    /**
     * Instead of using the generic {@link Authentication} from Spring, we can also convert the
     * {@link Authentication#getPrincipal()} into our custom {@code User} to access custom methods such as
     * {@link User#getEmail()}.
     */
    @GetMapping("/secure")
    String secure(Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof User user) { // Converts getPrincipal() to User and stores the object into the variable user that is accessible within the following if-block.
            model.addAttribute(USERNAME_KEY, user.getUsername());
            model.addAttribute(AUTHORITIES_KEY, user.getAuthorities());
            model.addAttribute(EMAIL_KEY, user.getEmail());
        } else {
            model.addAttribute(USERNAME_KEY, authentication.getName());
            model.addAttribute(AUTHORITIES_KEY, authentication.getAuthorities());
        }
        return "secure";
    }

    @GetMapping("/admin")
    String admin(Model model, Authentication authentication) {
        model.addAttribute(USERNAME_KEY, authentication.getName());
        model.addAttribute(AUTHORITIES_KEY, authentication.getAuthorities());
        return "admin";
    }

    @GetMapping("/module")
    public String moduleView() {
        return "module"; // schaut in templates/module.mustache
    }

    @GetMapping("/semester")
    public String semester() {
        return "semester";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("dramaturgie1")
    public String dramaturgie1() {
        return "module/dramaturgie1";
    }

    @GetMapping("informatik1")
    public String informatik1() {
        return "module/informatik1";
    }

    @GetMapping("mathe1")
    public String mathe1() {
        return "module/mathe1";
    }

    @GetMapping("media_game_design1")
    public String mgd1() {
        return "module/media_game_design1";
    }

    @GetMapping("medienrecht")
    public String medienrecht() {
        return "module/medienrecht";
    }

    @GetMapping("programmieren1")
    public String programmieren1() {
        return "module/programmieren1";
    }

    @GetMapping("sommersemester25")
    public String sose25() {
        return "module/sommersemester25";
    }

    @GetMapping("wintersemester24_25")
    public String wise2425() {
        return "module/wintersemester24_25";
    }

    @GetMapping("feedback_form")
    public String feedbackForm() {
        return "feedback_form";
    }

    @GetMapping("feedbacks")
    public String feedbacks() {return "feedbacks";}
}
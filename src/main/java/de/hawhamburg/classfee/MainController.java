package de.hawhamburg.classfee;

import de.hawhamburg.classfee.feedback.Feedback;
import de.hawhamburg.classfee.feedback.FeedbackRepository;
import de.hawhamburg.classfee.feedback.Module;
import de.hawhamburg.classfee.feedback.ModuleService;
import de.hawhamburg.classfee.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
class MainController {

    private static final String AUTHENTICATED_KEY = "authenticated";
    private static final String USERNAME_KEY = "username";
    private static final String AUTHORITIES_KEY = "authorities";
    private static final String EMAIL_KEY = "email";
    private final FeedbackRepository feedbackRepository;
    private final ModuleService moduleService;

    MainController(FeedbackRepository feedbackRepository, ModuleService moduleService) {
        this.feedbackRepository = feedbackRepository;
        this.moduleService = moduleService;
    }

//    @GetMapping("/")
//    String landingPage(Model model, Authentication authentication) {
//        if (authentication == null) {
//            model.addAttribute(AUTHENTICATED_KEY, false);
//        } else {
//            model.addAttribute(AUTHENTICATED_KEY, authentication.isAuthenticated());
//        }
//        return "index";
//    }

    @GetMapping("/")
    String landingPage(Model model, Authentication authentication) {
        if (authentication == null) {
            model.addAttribute("authenticated", false);
            model.addAttribute("isAdmin", false);
        } else {
            model.addAttribute("authenticated", authentication.isAuthenticated());
            model.addAttribute("isAdmin",
                    authentication.getAuthorities().stream()
                            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        }

        // Letzte 5 Feedbacks

        List<Feedback> latestFeedbacks = feedbackRepository.findTop5ByOrderByCreatedAtDesc();
        model.addAttribute("latestFeedbacks", latestFeedbacks);

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

    @GetMapping("/module/dramaturgie1")
    public String dramaturgie1(Model model) {

        List<Feedback> feedbacks = feedbackRepository.findByModuleName("Dramaturgie 1");
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("moduleName", "Dramaturgie 1");

        return "module/dramaturgie1";
    }

    @GetMapping("/module/informatik1")
    public String informatik1(Model model) {
        List<Feedback> feedbacks = feedbackRepository.findByModuleName("Informatik 1");
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("moduleName", "Informatik 1");
        return "module/informatik1";
    }

    @GetMapping("/module/mathe1")
    public String mathe1(Model model) {
        List<Feedback> feedbacks = feedbackRepository.findByModuleName("Mathematik 1");
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("moduleName", "Mathematik 1");
        return "module/mathe1";
    }

    @GetMapping("/module/media_game_design1")
    public String mgd1(Model model) {

        List<Feedback> feedbacks = feedbackRepository.findByModuleName("Media Game Design 1");
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("moduleName", "Media Game Design 1");

        return "module/media_game_design1";
    }

    @GetMapping("/module/medienrecht")
    public String medienrecht(Model model) {

        List<Feedback> feedbacks = feedbackRepository.findByModuleName("Medienrecht");
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("moduleName", "Medienrecht");

        return "module/medienrecht";
    }

    @GetMapping("/module/programmieren1")
    public String programmieren1(Model model) {

        List<Feedback> feedbacks = feedbackRepository.findByModuleName("Programmieren 1");
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("moduleName", "Programmieren 1");

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

    @GetMapping("/feedback_form")
    public String feedbackForm(Model model) {
        List<Module> modules = moduleService.getAllModules();
        model.addAttribute("modules", modules);
        return "feedback_form";
    }


    @GetMapping("feedbacks")
    public String feedbacks(Model model) {
        // Letzte 5 Feedbacks

        List<Feedback> latestFeedbacks = feedbackRepository.findTop5ByOrderByCreatedAtDesc();
        model.addAttribute("latestFeedbacks", latestFeedbacks);


        return "feedbacks";}
}
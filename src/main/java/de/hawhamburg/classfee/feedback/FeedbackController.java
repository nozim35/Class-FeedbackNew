package de.hawhamburg.classfee.feedback;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final ModuleAuswahl moduleAuswahl;

    public FeedbackController(FeedbackRepository feedbackRepository, ModuleAuswahl moduleAuswahl) {
        this.feedbackRepository = feedbackRepository;
        this.moduleAuswahl = moduleAuswahl;
    }

    @PostMapping("/new")
    public String submitFeedback(@RequestParam Long moduleId,
                                 @RequestParam String content,
                                // @RequestParam String semester,
                                 @RequestParam(required = false) boolean anonymous,
                                 Authentication authentication) {


        String author = anonymous || authentication == null
                ? "Anonym"
                : authentication.getName();

        String moduleName = moduleAuswahl.getModuleNameById(moduleId);             //Modulename

        Feedback feedback = new Feedback(content, author, LocalDateTime.now(), moduleId);
        feedback.setModuleName(moduleName);
        feedbackRepository.save(feedback);

        return "redirect:/";
    }
}

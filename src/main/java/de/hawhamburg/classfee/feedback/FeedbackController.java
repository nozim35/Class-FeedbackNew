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
    private final ModuleService moduleService;

    public FeedbackController(FeedbackRepository feedbackRepository, ModuleService moduleService) {
        this.feedbackRepository = feedbackRepository;
        this.moduleService = moduleService;
    }

    @PostMapping("/new")
    public String submitFeedback(@RequestParam Long moduleId,
                                 @RequestParam String content,
                                 @RequestParam(required = false) boolean anonymous,
                                 Authentication authentication) {

        String author = anonymous || authentication == null
                ? "Anonym"
                : authentication.getName();

        String moduleName = moduleService.getModuleNameById(moduleId);             //Modulename

        Feedback feedback = new Feedback(content, author, LocalDateTime.now(), moduleId);
        feedback.setModuleName(moduleName);
        feedbackRepository.save(feedback);

        return "redirect:/";
    }
}

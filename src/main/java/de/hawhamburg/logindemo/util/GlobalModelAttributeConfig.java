package de.hawhamburg.logindemo.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributeConfig {

    @ModelAttribute
    public void addGlobalAttributes(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("authenticated", isAuthenticated);

        if (isAuthenticated) {
            model.addAttribute("username", authentication.getName());
        }
    }
}

package de.hawhamburg.logindemo.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LogoutController {

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
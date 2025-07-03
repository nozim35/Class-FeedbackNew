package de.hawhamburg.logindemo.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {

    private static final String LOGIN_ENDPOINT = "/login";

    @GetMapping(value = LOGIN_ENDPOINT, params = "!error")
    public String loginWithoutError() {
        return "login";
    }

    @GetMapping(value = LOGIN_ENDPOINT, params = "error=true")
    public String loginWithError(Model model) {
        model.addAttribute("error", true); // true: Zeige Default-Error in Template.
        return "login";
    }
}
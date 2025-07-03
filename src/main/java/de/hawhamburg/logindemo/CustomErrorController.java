package de.hawhamburg.logindemo;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller is only needed for custom error pages when deployed on an external webserver such as Tomcat.
 */
@Controller
class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        model.addAttribute("status", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        model.addAttribute("message", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        return "error";
    }
}
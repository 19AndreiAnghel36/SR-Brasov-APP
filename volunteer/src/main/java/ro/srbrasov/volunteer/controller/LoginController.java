package ro.srbrasov.volunteer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/authentication/login")
    public String showLoginPage(){
        return "form/login_form";
    }

}

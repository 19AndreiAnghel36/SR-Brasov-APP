package ro.srbrasov.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.repository.UserRepository;
import ro.srbrasov.volunteer.service.UserService;

@Controller
public class RegisterController {
    @Autowired
    private UserService service;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "register_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user){
        service.saveUser(user);
        return "register_success";
    }

    @GetMapping("/register_success")
    public String showSuccessPage(){
        return "register_success";
    }
}
package ro.srbrasov.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.error.EmailAlreadyExistsException;
import ro.srbrasov.volunteer.error.EmailOrPasswordInvalid;
import ro.srbrasov.volunteer.repository.UserRepository;
import ro.srbrasov.volunteer.service.UserService;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(){
        return "form/login_form";
    }

    @GetMapping("/info/general-info")
    public String showGeneralInfoPage(){return "info";}

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "form/register_form";
    }

    @PostMapping("/process-register")
    public String processRegister(@ModelAttribute ("user") User user, Model model){
        try {
            userService.saveUser(user);
            return "success_page/register_success";
        }
        catch (EmailAlreadyExistsException e){
            model.addAttribute("error", e.getMessage());
            return "form/register_form";
        }
    }

    @PostMapping("/process-login")
    public String processLogin(@ModelAttribute ("user") User user, Model model){
        try{
            String userEmail = user.getEmail();
            String userPassword = user.getPassword();
            userService.tryToLogin(userEmail, userPassword);
            return "index";
        }
        catch (EmailOrPasswordInvalid e){
            model.addAttribute("error", e.getMessage());
            return "form/login_form";
        }
    }

    @GetMapping("/register-success")
    public String showSuccessPage(){
        return "success_page/register_success";
    }

}

package ro.srbrasov.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.repository.UserRepository;
import ro.srbrasov.volunteer.service.UserService;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    private User user;

    @GetMapping("/user")
    public String showControlPanel(Model model){
        List<User> listUsers = repository.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users_control_panel";
    }

    @GetMapping("/give-admin/{id}")
    public String giveAdminToAUser(@PathVariable("id") Long userId){
        service.makeUserAdmin(userId);
        return "make_admin";
    }

    @GetMapping("/make-admin")
    public String showSuccessPage(){
        return "make_admin";
    }
}

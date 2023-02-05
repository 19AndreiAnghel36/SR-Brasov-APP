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

    @GetMapping("/admin/operation-success")
    public String showSuccessPage(){
        return "admin_operation_success";
    }

    @GetMapping("/users-control-panel")
    public String showControlPanel(Model model){
        List<User> listUsers = repository.findAll();
        model.addAttribute("listUsers", listUsers);
        return "control_panel/users_control_panel";
    }

    @GetMapping("/give-admin/{id}")
    public String giveAdminToAUser(@PathVariable("id") Long userId){
        service.makeUserAdmin(userId);
        return "success_page/admin_operation_success";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") Long userId){
        User user = repository.findById(userId).get();
        repository.delete(user);
        return "success_page/admin_operation_success";
    }

    @GetMapping("/retrieve-admin/{id}")
    public String retrieveAdmin(@PathVariable("id") Long userId){
        service.retrieveAdmin(userId);
        return "success_page/admin_operation_success";
    }
}
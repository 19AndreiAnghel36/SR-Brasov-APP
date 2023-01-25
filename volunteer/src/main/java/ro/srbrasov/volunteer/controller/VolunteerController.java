package ro.srbrasov.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ro.srbrasov.volunteer.entity.Volunteer;
import ro.srbrasov.volunteer.repository.VolunteerRepository;
import ro.srbrasov.volunteer.service.VolunteerService;

import java.util.List;


@Controller
public class VolunteerController {
    @Autowired
    private VolunteerRepository repository;
    @Autowired
    private VolunteerService service;

    @GetMapping("/volunteers")
    public String showVolunteersControlPanel(Model model){
        List<Volunteer> listVolunteers = repository.findAll();
        model.addAttribute("listVolunteers", listVolunteers);
        return "volunteer_control_panel";
    }

    @GetMapping("/become-volunteer/{jobId}")
    public String becomeVolunteer(@PathVariable("jobId") Long jobId) {
        service.becomeVolunteer(jobId);
        return "become_volunteer_form";
    }

    @GetMapping("/become-volunteer")
    public String showBecomeVolunteerForm(Model model, String phoneNumber){
        model.addAttribute("phoneNumber", phoneNumber);
        return "become_volunteer_form";
    }

    @PostMapping("/become-volunteer")
    public String processPhoneNumber(String phoneNumber){
        service.setPhoneNumber(phoneNumber);
        return "success_page";
    }

}
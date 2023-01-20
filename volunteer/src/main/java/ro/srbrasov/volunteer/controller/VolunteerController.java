package ro.srbrasov.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.srbrasov.volunteer.service.VolunteerService;


@Controller
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("/become-volunteer/{jobId}")
    public String becomeVolunteer(@PathVariable("jobId") Long jobId) {
        volunteerService.becomeVolunteer(jobId);
        return "success_page";
    }


}

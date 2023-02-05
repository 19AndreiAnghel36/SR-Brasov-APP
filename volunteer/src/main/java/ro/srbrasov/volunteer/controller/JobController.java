package ro.srbrasov.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ro.srbrasov.volunteer.entity.Job;
import ro.srbrasov.volunteer.repository.JobRepository;

import java.util.List;

@Controller
public class JobController {
    @Autowired
    private JobRepository repository;

    @GetMapping("/available-jobs")
    public String showJobControlPanel(Model model) {
        List<Job> listJobs = repository.findAll();
        model.addAttribute("listJobs", listJobs);
        return "control_panel/job_control_panel";
    }

    @GetMapping("/add-job")
    public String showJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "form/job_form";
    }

    @PostMapping("/add-job")
    public String addEvent(Job job) {
        repository.save(job);
        return "success_page/job_operation_success";
    }

    @GetMapping("/delete-job/{id}")
    public String deleteEvent(@PathVariable("id") Long jobId) {
        Job job = repository.findById(jobId).get();
        repository.delete(job);
        return "success_page/job_operation_success";
    }

    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable("id") Long jobId) {
        Job job = repository.findById(jobId).get();
        if (job.getId() == 1) {
            return "steward_details";
        } else if (job.getId() == 2) {
            return "ball_kid_details";
        } else if (job.getId() == 3) {
            return "stretcher-bearer_details";
        } else if (job.getId() == 4) {
            return "steward_responsible_details";
        } else {
            return "ticket_seller_details";
        }
    }
}
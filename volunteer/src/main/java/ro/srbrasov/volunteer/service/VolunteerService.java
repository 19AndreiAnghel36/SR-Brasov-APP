package ro.srbrasov.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.srbrasov.volunteer.entity.Job;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.repository.JobRepository;
import ro.srbrasov.volunteer.repository.UserRepository;

@Service
public class VolunteerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;

    public void becomeVolunteer(Long jobId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);

        Job job = jobRepository.findById(jobId).get();

        user.addJob(job);

        userRepository.save(user);
    }
}

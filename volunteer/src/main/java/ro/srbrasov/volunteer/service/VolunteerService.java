package ro.srbrasov.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.srbrasov.volunteer.entity.Job;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.entity.Volunteer;
import ro.srbrasov.volunteer.repository.JobRepository;
import ro.srbrasov.volunteer.repository.UserRepository;
import ro.srbrasov.volunteer.repository.VolunteerRepository;

@Service
public class VolunteerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;

    /**
     * Add volunteer to database.
     * @param jobId - job id.
     */
    public void becomeVolunteer(Long jobId) {
        // get logged-in user information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // find logged-in user in database
        User user = userRepository.findByEmail(userDetails.getUsername());
        // find job in database
        Job job = jobRepository.findById(jobId).get();
        // check job's availability
        checkVolunteerNumbers(job);
        // add job to user and save user to database
        user.setJob(job);
        userRepository.save(user);
        // create new volunteer and save it in database
        Volunteer volunteer = new Volunteer(user, jobRepository.findById(jobId).get());
        volunteerRepository.save(volunteer);
    }

    /**
     * Add user's phone number.
     * @param phoneNumber - phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        // get logged-in user information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // find logged-in user in database
        User user = userRepository.findByEmail(userDetails.getUsername());
        // find volunteer associated with this user id
        Volunteer volunteer = volunteerRepository.findByUserId(user.getId());
        // set his phone number and save it
        volunteer.setPhoneNumber(phoneNumber);
        volunteerRepository.save(volunteer);
    }

    private void checkVolunteerNumbers(Job job){
        if (job.getVolunteersCount() >= job.getMaxVolunteers()) {
            throw new IllegalStateException("Numarul de voluntari pentru aceasta pozitie a fost atins. Puteti incerca o alta pozitite. Multumim!");
        }
        job.setVolunteersCount(job.getVolunteersCount() + 1);
    }
}
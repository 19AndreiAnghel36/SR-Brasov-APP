package ro.srbrasov.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.srbrasov.volunteer.entity.Role;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.error.EmailAlreadyExistsException;
import ro.srbrasov.volunteer.repository.RoleRepository;
import ro.srbrasov.volunteer.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Save user to database with encrypted password and role 'User' by default.
     * @param user - user that will be saved.
     */
    public void saveUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userEmail = user.getEmail();

        if(userRepository.existsByEmail(userEmail)){
            throw new EmailAlreadyExistsException(userEmail);
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role userRole = roleRepository.findByName("User");

        User userReturned = User.builder()
                .email(userEmail)
                .password(encodedPassword)
                .role(userRole)
                .loginCounter(0)
                .isLocked(false)
                .build();

        userRepository.save(userReturned);
    }

    /**
     * Check if 30 min ban has passed.
     * @param user - user that is checked
     * @return - true if ban has passed or false if it is not.
     */
    public boolean canAccountBeUnlocked(User user){
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(user.getLockEndTime());
    }

    /**
     * Give 'Admin' to a user.
     * @param userId - user id
     */
    public void makeUserAdmin(Long userId){
        User user = userRepository.findById(userId).get();
        user.setRole(roleRepository.findByName("Admin"));
        userRepository.save(user);
    }

    /**
     * Retrieve 'Admin' from a user and make it 'User'.
     * @param userId - user id.
     */
    public void retrieveAdmin(Long userId){
        User user = userRepository.findById(userId).get();
        user.setRole(roleRepository.findByName("User"));
        userRepository.save(user);
    }
}

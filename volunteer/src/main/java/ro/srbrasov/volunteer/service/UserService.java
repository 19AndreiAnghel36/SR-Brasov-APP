package ro.srbrasov.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.srbrasov.volunteer.entity.Role;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.repository.RoleRepository;
import ro.srbrasov.volunteer.repository.UserRepository;

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
        // create password encoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // encode user password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        // set the encoded password on the user object
        user.setPassword(encodedPassword);
        // retrieve the 'User' role from the repository
        Role userRole = roleRepository.findByName("User");
        // add the 'User' role to the user object
        user.setRole(userRole);
        // save the user to the database
        userRepository.save(user);
    }

    /**
     * Give 'Admin' to a user.
     * @param userId - user id
     */
    public void makeUserAdmin(Long userId){
        // find the user
        User user = userRepository.findById(userId).get();
        // set 'Admin' role
        user.setRole(roleRepository.findByName("Admin"));
        // save the user to the database
        userRepository.save(user);
    }

    /**
     * Retrieve 'Admin' from a user and make it 'User'.
     * @param userId - user id.
     */
    public void retrieveAdmin(Long userId){
        // find the user
        User user = userRepository.findById(userId).get();
        // set 'User' role
        user.setRole(roleRepository.findByName("User"));
        // save the user to the database
        userRepository.save(user);
    }
}

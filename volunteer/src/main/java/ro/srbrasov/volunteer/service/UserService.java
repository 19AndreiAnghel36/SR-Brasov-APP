package ro.srbrasov.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.srbrasov.volunteer.entity.Role;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.error.EmailAlreadyExistsException;
import ro.srbrasov.volunteer.error.EmailOrPasswordInvalid;
import ro.srbrasov.volunteer.repository.RoleRepository;
import ro.srbrasov.volunteer.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

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
        user.setPassword(encodedPassword);
        Role userRole = roleRepository.findByName("User");
        user.setRole(userRole);

        userRepository.save(user);
    }

    public void tryToLogin(String email,
                           String password){
        User user = userRepository.findByEmail(email);

        if(user == null || !encoder.matches(password, user.getPassword())){
            throw new EmailOrPasswordInvalid("Email sau parola introduse gresit!");
        }

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

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

    public void saveUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role role = roleRepository.findByName("User");
        user.addRole(role);
        userRepository.save(user);
    }
}

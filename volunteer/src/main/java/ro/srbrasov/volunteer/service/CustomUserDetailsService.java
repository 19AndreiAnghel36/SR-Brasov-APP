package ro.srbrasov.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.repository.UserRepository;
import ro.srbrasov.volunteer.utils.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Nu exista un cont cu aceasta adresa de email!");
        }
        return new CustomUserDetails(user);
    }
}

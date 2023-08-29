package ro.srbrasov.volunteer.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.repository.UserRepository;

import java.io.IOException;

@Component
public class CustomLoginSuccess extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = request.getParameter("email");
        if (email != null && !email.isEmpty()) {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                user.setLoginCounter(0);
                userRepository.save(user);
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

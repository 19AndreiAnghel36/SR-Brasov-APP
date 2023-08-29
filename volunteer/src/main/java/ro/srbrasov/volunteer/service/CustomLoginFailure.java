package ro.srbrasov.volunteer.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ro.srbrasov.volunteer.entity.User;
import ro.srbrasov.volunteer.error.AccountWasLockedException;
import ro.srbrasov.volunteer.error.EmailOrPasswordInvalid;
import ro.srbrasov.volunteer.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class CustomLoginFailure extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");
        User user = userRepository.findByEmail(email);
        if(user != null){
            if(!user.isLocked()) {
                if (user.getLoginCounter() < 5) {
                    user.setLoginCounter(user.getLoginCounter() + 1);
                    userRepository.save(user);
                } else {
                    LocalDateTime now = LocalDateTime.now();
                    user.setLocked(true);
                    user.setLockEndTime(now.plusMinutes(30));
                    userRepository.save(user);
                    throw new AccountWasLockedException("Contul a fost blocat!");
                }
                exception = new EmailOrPasswordInvalid("Email sau parola introduse gresit!");
            }
            else {
                if (userService.canAccountBeUnlocked(user)){
                    user.setLocked(false);
                    user.setLockEndTime(null);
                    userRepository.save(user);
                }
            }
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}

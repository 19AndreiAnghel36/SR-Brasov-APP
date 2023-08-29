package ro.srbrasov.volunteer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ro.srbrasov.volunteer.service.CustomLoginFailure;
import ro.srbrasov.volunteer.service.CustomLoginSuccess;
import ro.srbrasov.volunteer.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    CustomLoginFailure loginFailure;

    @Autowired
    CustomLoginSuccess loginSuccess;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetails() {
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/authentication/register", "/authentication/register/process", "/authentication/register/success").permitAll()
                                .anyRequest().authenticated()
                        )
                .formLogin((formLogin) ->
                        formLogin
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .loginPage("/authentication/login").permitAll()
                                .failureUrl("/authentication/login?failed")
                                .loginProcessingUrl("/authentication/login/process")
                                .defaultSuccessUrl("/", true)
                                .failureHandler(loginFailure)
                                .successHandler(loginSuccess)
                );
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetails());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

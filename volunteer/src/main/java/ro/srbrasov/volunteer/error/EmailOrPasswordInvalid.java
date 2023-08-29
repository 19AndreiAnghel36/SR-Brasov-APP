package ro.srbrasov.volunteer.error;

import org.springframework.security.core.AuthenticationException;

public class EmailOrPasswordInvalid extends AuthenticationException {
    public EmailOrPasswordInvalid(String message){
        super(message);
    }
}

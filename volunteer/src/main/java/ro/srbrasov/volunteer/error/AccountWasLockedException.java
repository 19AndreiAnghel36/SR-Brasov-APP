package ro.srbrasov.volunteer.error;

import org.springframework.security.core.AuthenticationException;

public class AccountWasLockedException extends AuthenticationException {
    public AccountWasLockedException(String message){super(message);}
}

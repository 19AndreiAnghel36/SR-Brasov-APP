package ro.srbrasov.volunteer.error;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String email){
        super("Adresa de email '" + email + "' există deja în sistem.");
    }
}

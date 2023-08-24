package ro.srbrasov.volunteer.error;

public class EmailOrPasswordInvalid extends RuntimeException{
    public EmailOrPasswordInvalid(String message){
        super(message);
    }
}

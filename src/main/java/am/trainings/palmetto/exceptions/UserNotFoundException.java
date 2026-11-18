package am.trainings.palmetto.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super (message);
    }
}

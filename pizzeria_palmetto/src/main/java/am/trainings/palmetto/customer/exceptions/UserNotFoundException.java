package am.trainings.palmetto.customer.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super (message);
    }
}

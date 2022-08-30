package wolox.training.exceptions;

public class UsernameMismatchException extends RuntimeException{
    public UsernameMismatchException(String errorMessage){
        super(errorMessage);
    }
}

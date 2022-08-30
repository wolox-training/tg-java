package wolox.training.exceptions;

public class BookAlreadyOwnedException extends RuntimeException{
    public BookAlreadyOwnedException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }

}

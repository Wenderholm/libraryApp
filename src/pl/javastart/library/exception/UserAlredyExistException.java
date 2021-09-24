package pl.javastart.library.exception;

public class UserAlredyExistException extends RuntimeException{
    public UserAlredyExistException(String message) {
        super(message);
    }
}

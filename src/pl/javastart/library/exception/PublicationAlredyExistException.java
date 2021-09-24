package pl.javastart.library.exception;

public class PublicationAlredyExistException extends RuntimeException{
    public PublicationAlredyExistException(String message) {
        super(message);
    }
}

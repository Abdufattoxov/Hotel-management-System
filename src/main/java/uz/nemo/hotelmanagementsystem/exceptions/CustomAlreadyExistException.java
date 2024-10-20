package uz.nemo.hotelmanagementsystem.exceptions;

public class CustomAlreadyExistException extends RuntimeException {
    public CustomAlreadyExistException(String message) {
        super(message);
    }
}

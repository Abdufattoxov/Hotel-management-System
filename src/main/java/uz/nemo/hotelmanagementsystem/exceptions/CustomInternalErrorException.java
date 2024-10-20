package uz.nemo.hotelmanagementsystem.exceptions;

public class CustomInternalErrorException extends RuntimeException{

    public CustomInternalErrorException(String message) {
        super(message);
    }
}

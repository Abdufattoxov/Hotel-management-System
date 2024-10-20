package uz.nemo.hotelmanagementsystem.exceptions;

public class CustomBadRequestException extends RuntimeException {
  public CustomBadRequestException(String message) {
    super(message);
  }
}

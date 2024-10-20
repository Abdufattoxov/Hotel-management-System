package uz.nemo.hotelmanagementsystem.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.nemo.hotelmanagementsystem.payload.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({CustomNotFoundException.class})
    public ApiErrorResponse handleNotFoundException(CustomNotFoundException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CustomIllegalArgumentException.class})
    public ApiErrorResponse handleCustomIllegalArgumentException(CustomIllegalArgumentException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CustomBadRequestException.class})
    public ApiErrorResponse handleCustomBadRequestException(CustomBadRequestException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class})
    public ApiErrorResponse handleBadCredentialsException(BadCredentialsException e) {
        return new ApiErrorResponse("user name or password is incorrect");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ExpiredJwtException.class})
    public ApiErrorResponse handleExpiredJwtException(ExpiredJwtException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({SignatureException.class})
    public ApiErrorResponse handleSignatureException(SignatureException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({CustomAlreadyExistException.class})
    public ApiErrorResponse handleCustomAlreadyExistException(CustomAlreadyExistException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({CustomConflictException.class})
    public ApiErrorResponse handleAlreadyExistException(CustomConflictException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({CustomInternalErrorException.class})
    public ApiErrorResponse handleInternalErrorException(CustomInternalErrorException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

}

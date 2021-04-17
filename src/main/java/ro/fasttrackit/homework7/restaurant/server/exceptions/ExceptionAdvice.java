package ro.fasttrackit.homework7.restaurant.server.exceptions;

import lombok.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ApiError handleResourceNotFoundException(ResourceNotFoundException rnfe) {
        return new ApiError(rnfe.getMessage());
    }
}

@Value
class ApiError {
    String message;
}

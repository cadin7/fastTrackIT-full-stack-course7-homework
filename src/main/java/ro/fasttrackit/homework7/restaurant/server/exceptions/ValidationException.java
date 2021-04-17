package ro.fasttrackit.homework7.restaurant.server.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

package pl.coderslab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BaseEntityNotFoundException extends RuntimeException {

    private static final String MESSAGE_PATTERN = "Entity with id %s can not be found";

    public BaseEntityNotFoundException(Long id) {
        super(String.format(MESSAGE_PATTERN, id));

    }

    public BaseEntityNotFoundException(String message) {
        super(message);
    }

}

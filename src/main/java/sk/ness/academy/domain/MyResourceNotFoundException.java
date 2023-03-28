package sk.ness.academy.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MyResourceNotFoundException extends RuntimeException {
    private String message;
    public MyResourceNotFoundException() {}
    public MyResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}

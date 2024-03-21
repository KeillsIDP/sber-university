package me.keills.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Неверный пароль!")
public class UserIncorrectPassword extends RuntimeException {
    public UserIncorrectPassword(String message) {
        super(message);
    }
}

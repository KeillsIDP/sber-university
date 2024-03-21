package me.keills.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Пользователь с таким именем уже существует!")
public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String msg) {
        super(msg);
    }
}

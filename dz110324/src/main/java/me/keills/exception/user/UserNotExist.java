package me.keills.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Пользователя с таким именем не существует!")
public class UserNotExist extends RuntimeException {
    public UserNotExist(String msg) {
        super(msg);
    }
}

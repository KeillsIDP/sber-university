package me.keills.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Пользователь не имеет карты!")
public class UserDontHaveCard extends RuntimeException {
    public UserDontHaveCard(String message) {
        super(message);
    }
}

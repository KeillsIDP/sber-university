package me.keills.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Пользователь уже имеет карту!")
public class UserAlreadyHaveCard extends RuntimeException{
    public UserAlreadyHaveCard(String msg){
        super(msg);
    }
}

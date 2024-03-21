package me.keills.exception.card;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Карты не существует!")
public class CardDontExist extends RuntimeException{
    public CardDontExist(String msg){
        super(msg);
    }
}

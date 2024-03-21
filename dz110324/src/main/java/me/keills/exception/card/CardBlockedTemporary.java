package me.keills.exception.card;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Карта заблокирована!")
public class CardBlockedTemporary extends RuntimeException {
    public CardBlockedTemporary(String msg) {
        super(msg);
    }
}

package me.keills.exception.card;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Недостаточно средств!")
public class CardDontContainEnoughMoney extends RuntimeException {
    public CardDontContainEnoughMoney(String msg) {
        super(msg);
    }
}

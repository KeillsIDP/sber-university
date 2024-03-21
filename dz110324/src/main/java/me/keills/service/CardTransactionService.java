package me.keills.service;

import me.keills.model.Card;

public interface CardTransactionService {
    boolean authentication(Long cardId, String pin);

    void putMoney(Long cardId, Long amount);

    void getMoney(Long cardId, Long amount);

    Long checkMoney(Long cardId);
}

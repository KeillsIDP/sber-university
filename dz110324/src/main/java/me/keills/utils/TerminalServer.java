package me.keills.utils;

import me.keills.exception.NotEnoughMoneyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TerminalServer {
    private final IllegalArgumentException BALANCE_INVALID_ARGUMENT_EXCEPTION = new IllegalArgumentException("Вводите только положительные значения при работе с балансом!");
    private final NotEnoughMoneyException NOT_ENOUGH_MONEY_ON_BALANCE_EXCEPTION = new NotEnoughMoneyException("Недостаточно средств на счету!");
    private int balance;

    public int getBalance() {
        return balance;
    }

    public void addBalance(int value) {
        if(value<=0)
            throw BALANCE_INVALID_ARGUMENT_EXCEPTION;
        this.balance += value;
    }

    public void decreaseBalance(int value) {
        if(value<=0)
            throw BALANCE_INVALID_ARGUMENT_EXCEPTION;
        else if(value>balance)
            throw NOT_ENOUGH_MONEY_ON_BALANCE_EXCEPTION;

        this.balance -= value;
    }

}

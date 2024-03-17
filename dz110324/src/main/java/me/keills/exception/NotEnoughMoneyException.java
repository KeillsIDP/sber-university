package me.keills.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String msg){
        super(msg);
    }
}

package me.keills.exception;

public class IncorrectPinException extends RuntimeException{
    public IncorrectPinException(String msg){
        super(msg);
    }
}

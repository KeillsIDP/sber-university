package me.keills.exception;

public class IncorrectPinLengthException extends RuntimeException{
    public IncorrectPinLengthException(String msg){
        super(msg);
    }
}

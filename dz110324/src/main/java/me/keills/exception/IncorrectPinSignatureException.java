package me.keills.exception;

public class IncorrectPinSignatureException extends RuntimeException{
    public IncorrectPinSignatureException(String msg){
        super(msg);
    }
}

package dz281223.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String msg){
        super(msg);
    }
}
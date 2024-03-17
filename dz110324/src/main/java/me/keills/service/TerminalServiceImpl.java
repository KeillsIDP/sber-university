package me.keills.service;

public class TerminalServiceImpl implements TerminalService {
    private final TerminalServer server;
    private final PinValidator pinValidator;
    private final IllegalArgumentException TERMINAL_INVALID_MONEY_VALUE_EXCEPTION = new IllegalArgumentException("Вводимое значение должно быть кратно 100!");
    private String pin;
    public TerminalServiceImpl(TerminalServer server, PinValidator pinValidator){
        this.server = server;
        this.pinValidator = pinValidator;
        pin = RandomPinGenerator.generate();
        System.out.println("Внимание! Ваш PIN-код: " + pin);
    }

    @Override
    public int checkBalance() {
        return server.getBalance();
    }

    @Override
    public boolean putMoney(int value) {
        pinValidator.getAccessToTransaction(pin);
        if(value%100!=0)
            throw TERMINAL_INVALID_MONEY_VALUE_EXCEPTION;

        try{
            server.addBalance(value);
        }
        catch (IllegalArgumentException e){
            ExceptionHandler.handle(e);
        }

        return true;
    }

    @Override
    public boolean retrieveMoney(int value) {
        pinValidator.getAccessToTransaction(pin);
        if(value%100!=0)
            throw TERMINAL_INVALID_MONEY_VALUE_EXCEPTION;

        try{
            server.decreaseBalance(value);
        }
        catch (IllegalArgumentException e){
            ExceptionHandler.handle(e);
        }

        return true;
    }
}

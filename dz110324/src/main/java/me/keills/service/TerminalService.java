package me.keills.service;

public interface TerminalService {
    int checkBalance();
    boolean putMoney(int value);
    boolean retrieveMoney(int value);
}

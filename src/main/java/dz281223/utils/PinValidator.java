package dz281223.utils;

import dz281223.exception.IncorrectPinException;
import dz281223.exception.IncorrectPinLengthException;
import dz281223.exception.IncorrectPinSignatureException;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PinValidator {
    private final IncorrectPinLengthException INCORRECT_PIN_LENGTH_EXCEPTION = new IncorrectPinLengthException("Длина заданного PIN-кода неверна,он должен содержать 4 цифры!");
    private final IncorrectPinSignatureException INCORRECT_PIN_SIGNATURE_EXCEPTION = new IncorrectPinSignatureException("PIN-код может содержать тольцо цифры!");
    private final IncorrectPinException INCORRECT_PIN_EXCEPTION = new IncorrectPinException("PIN-код не совпадает с верным!");
    private int loginAttempts = 0;
    private boolean blocked = false;
    private long blockEnd;
    public boolean getAccessToTransaction(String correctPin){
        System.out.println("Пожалуйста введите PIN-код вашей сессии.");
        while(true){
            if(blocked){
                System.out.println("Нам пришлось заблокировать операции с вашей картой из-за подозрительной активности! Блокировка снимется через " + (System.currentTimeMillis() - blockEnd) / 1000 + " секунд");
                continue;
            }

            try{
                String inputPin = inputPin();

                if(validate(inputPin)){
                    if(inputPin.equals(correctPin)){
                        System.out.println("PIN-код введен верно, продолжаем операцию...");
                        return true;
                    }

                    throw INCORRECT_PIN_EXCEPTION;
                }
                throw INCORRECT_PIN_SIGNATURE_EXCEPTION;
            }
            catch (IncorrectPinLengthException e){
                System.out.println(e.getMessage());
            }
            catch (IncorrectPinSignatureException e){
                System.out.println(e.getMessage());
            }
            catch (IncorrectPinException e){
                System.out.println(e.getMessage());
                if(blocked && System.currentTimeMillis()>blockEnd){
                    blocked = false;
                    loginAttempts = 0;
                }
                else if(!blocked){
                    loginAttempts++;
                    int remainingAttempts = (3-loginAttempts);
                    if(loginAttempts>=3){
                        blocked = true;
                        blockEnd = System.currentTimeMillis() + 10000;
                    }
                    else
                        System.out.println("У вас осталось " + remainingAttempts + (remainingAttempts==1?" попытка":" попытки"));
                }
            }
        }
    }
    private String inputPin(){
        Scanner scan = new Scanner(System.in);
        String pin = scan.nextLine();
        if(pin.length()!=4)
            throw INCORRECT_PIN_LENGTH_EXCEPTION;

        return pin;
    }
    private boolean validate(String pin){
        return pin.chars().filter(c->Character.isDigit(c)).count() == 4;
    }
}

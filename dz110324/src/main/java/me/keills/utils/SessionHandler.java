package me.keills.utils;

import java.util.Scanner;

public class SessionHandler {
    private final Terminal terminal;

    public SessionHandler(Terminal terminal){
        this.terminal = terminal;
    }

    public void startSession(){
        System.out.println("Приветствуем вас!");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while(running){
            System.out.println("Доступные операции: \n1.Вывести денег\n2.Внести деньги\n3.Проверка баланса\n4.Выход\n");
            System.out.print("Ввод:");
            int commandId = scanner.nextInt();
            System.out.println();
            switch (commandId){
                case 1: {
                    System.out.println("Введите сумму вывода. Она должна быть кратна 100.");
                    int value = scanner.nextInt();
                    try {
                        terminal.retrieveMoney(value);
                    }
                    catch (IllegalArgumentException e){
                        ExceptionHandler.handle(e);
                    }
                    catch (NotEnoughMoneyException e){
                        ExceptionHandler.handle(e);
                    }
                    break;
                }
                case 2: {
                    System.out.println("Введите сумму ввода. Она должна быть кратна 100.");
                    int value = scanner.nextInt();
                    try{
                        terminal.putMoney(value);
                    }
                    catch (IllegalArgumentException e){
                        ExceptionHandler.handle(e);
                    }
                    break;
                }
                case 3: {
                    System.out.println("Ваш баланс: " + terminal.checkBalance());
                    break;
                }
                case 4: {
                    System.out.println("До новых встреч!");
                    running = false;
                    break;
                }
                default:
                    System.out.println("Неизвестная операция! Используйте доступные в меню операции!");
                    break;
            }
            System.out.println();
        }
    }
}

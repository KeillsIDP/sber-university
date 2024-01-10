package dz281223.utils;

import dz281223.exception.NotEnoughMoneyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TerminalServerTest {

    @Test
    public void addBalance_ExceptionTest(){
        TerminalServer terminalServer = new TerminalServer();
        assertThrows(IllegalArgumentException.class,()->{
            terminalServer.addBalance(-100);
        });
    }

    @Test
    public void decreaseBalance_NotEnoughMoneyExceptionTest(){
        TerminalServer terminalServer = new TerminalServer();
        assertThrows(NotEnoughMoneyException.class,()->{
            terminalServer.decreaseBalance(100);
        });
    }

    @Test
    public void addBalance_IllegalArgumentExceptionTest(){
        TerminalServer terminalServer = new TerminalServer();
        assertThrows(IllegalArgumentException.class,()->{
            terminalServer.decreaseBalance(-100);
        });
    }
}
package dz281223;

import dz281223.terminal.Terminal;
import dz281223.terminal.TerminalImpl;
import dz281223.utils.PinValidator;
import dz281223.utils.SessionHandler;
import dz281223.utils.TerminalServer;



public class Main {
    public static void main(String[] args){
        Terminal terminal = new TerminalImpl(new TerminalServer(),new PinValidator());
        SessionHandler session = new SessionHandler(terminal);

        session.startSession();
    }

}

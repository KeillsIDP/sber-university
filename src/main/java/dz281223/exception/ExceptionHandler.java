package dz281223.exception;

public class ExceptionHandler {
    public static void handle(RuntimeException e){
        System.out.println(e.getMessage()+"\n");
    }
}

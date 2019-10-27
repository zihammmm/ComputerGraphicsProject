package expections;

public class CommandNotFound extends Exception {
    public CommandNotFound(String cmd){
        super("Command doesn't exsit :" + cmd);
    }
}

package expections;

public class ParameterError extends Exception {
    public ParameterError(){
        super("The parameter exceeds the range ");
    }
}

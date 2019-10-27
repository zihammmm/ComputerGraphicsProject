package expections;

public class NullPixelWriter extends Exception {
    public NullPixelWriter(){
        super("The pixelWriter is null");
    }
}

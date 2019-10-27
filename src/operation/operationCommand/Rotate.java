package operation.operationCommand;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;

public class Rotate extends OperationCommand {
    private int x;

    private int y;

    private int r;

    public Rotate(int i, int xx, int yy, int rr){
        super(i);
        x = xx;
        y = yy;
        r = rr;
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/

    @Override
    public boolean paint(GraphicsContext gc) {
        return true;
    }
}

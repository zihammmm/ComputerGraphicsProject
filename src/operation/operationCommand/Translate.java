package operation.operationCommand;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;

public class Translate extends OperationCommand {
    private int dx;

    private int dy;

    public Translate(int i, int x, int y){
        super(i);

        dx = x;
        dy = y;
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/

    @Override
    public boolean paint(GraphicsContext gc) {
        return true;
    }
}

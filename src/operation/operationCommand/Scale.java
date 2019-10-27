package operation.operationCommand;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;

public class Scale extends OperationCommand {
    private int x;

    private int y;

    private float s;

    public Scale(int i, int xx, int yy, float ss){
        super(i);
        x = xx;
        y = yy;
        s = ss;
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/

    @Override
    public boolean paint(GraphicsContext gc) {
        return true;
    }
}

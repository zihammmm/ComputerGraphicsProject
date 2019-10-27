package operation.primitiveCommand;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;

public class DrawEllipse extends PrimitiveCommand{
    private int x;

    private int y;

    private int rx;

    private int ry;

    public DrawEllipse(int i, int xx, int yy, int rrx, int rry){
        super(i);
        x = xx;
        y = yy;
        rx = rrx;
        ry = rry;
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/

    @Override
    public boolean paint(GraphicsContext gc) {
        return true;
    }
}

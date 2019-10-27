package operation.primitiveCommand;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;

public class DrawLine extends PrimitiveCommand {
    private int x1;

    private int x2;

    private int y1;

    private int y2;

    private String algorithmName;

    public DrawLine(int i, int xx1, int yy1, int xx2, int yy2, String an){
        super(i);
        x1 = xx1;
        y1 = yy1;
        x2 = xx2;
        y2 = yy2;
        algorithmName = an;
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/

    @Override
    public boolean paint(GraphicsContext gc) {
        gc.strokeLine(x1,y1,x2,y2);
        return true;
    }
}

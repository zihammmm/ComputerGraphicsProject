package operation.operationCommand;

import algorithm.Algorithm;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;
import sample.MyBrush;

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

    @Override
    public boolean paint() {
        Algorithm.rotate(x, y, r, pc);
        pc.calculatePoints();
        return true;
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/


}

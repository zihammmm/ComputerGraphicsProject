package operation.operationCommand;

import algorithm.Algorithm;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;
import sample.MyBrush;

public class Translate extends OperationCommand {
    private int dx;

    private int dy;

    public Translate(int i, int x, int y){
        super(i);

        dx = x;
        dy = y;
    }

    @Override
    public boolean paint() {
        Algorithm.translate(dx, dy, pc);
        pc.calculatePoints();

        return true;
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/


}

package operation.operationCommand;

import algorithm.Algorithm;
import javafx.scene.paint.Color;
import sample.MyBrush;


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

    @Override
    public boolean paint() {
        Algorithm.scale(x, y, s, pc);
        pc.calculatePoints();

        return true;
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/


}

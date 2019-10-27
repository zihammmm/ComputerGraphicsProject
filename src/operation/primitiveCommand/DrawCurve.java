package operation.primitiveCommand;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;

public class DrawCurve extends PrimitiveCommand{
    private int n;

    private String algorithmName;

    private int[] x;

    private int[] y;

    public DrawCurve(int i, int nn, int[] xx, int[] yy, String an){
        super(i);
        n = nn;
        algorithmName = an;
        x = new int[xx.length];
        y = new int[yy.length];

        System.arraycopy(xx, 0, x, 0, xx.length);
        System.arraycopy(yy, 0, y, 0, yy.length);
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/

    @Override
    public boolean paint(GraphicsContext gc) {
        double[] xx = new double[n];
        double[] yy = new double[n];
        for (int i = 0; i< n; i++){
            xx[i] = x[i];
            yy[i] = y[i];
        }

        gc.strokePolyline(xx, yy, n);
        return true;
    }
}

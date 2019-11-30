package operation.primitiveCommand;

import algorithm.CurveAlgorithm;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;
import sample.MyBrush;

import java.awt.*;

//曲线
public class DrawCurve extends PrimitiveCommand{
    private int n;

    private String algorithmName;

    private int[] pX;   //顶点

    private int[] pY;

    public DrawCurve(int i, int nn, int[] xx, int[] yy, String an, Color color){
        super(i, color);
        n = nn;
        algorithmName = an;
        pX = new int[xx.length];
        pY = new int[yy.length];

        System.arraycopy(xx, 0, pX, 0, xx.length);
        System.arraycopy(yy, 0, pY, 0, yy.length);
    }

    @Override
    public void calculatePoints() {
        if (algorithmName.equals("Bezier"))
            CurveAlgorithm.bezier(this);
        else if (algorithmName.equals("B-spline"))
            CurveAlgorithm.bSpline(this);
        x_min = y_min = 1000;
        x_max = x_min = 0;
        for (int i = 0; i < pX.length; i++){
            x_min = Math.min(x_min, pX[i]);
            x_max = Math.max(x_max, pX[i]);
            y_min = Math.min(y_min, pY[i]);
            y_max = Math.max(y_max, pY[i]);
        }
    }

    @Override
    public boolean paint() {
        MyBrush.setColor(color);
        return false;
    }

    @Override
    public void translate(int dx, int dy) {
        for (int i = 0; i < n; i++){
            pX[i] += dx;
            pY[i] += dy;
        }
    }

    @Override
    public void rotate(int x, int y, int r) {
        int xx, yy;
        double cos = Math.cos(r), sin = Math.sin(r);
        for (int i = 0; i < n; i++){
            xx = pX[i];
            yy = pY[i];
            pX[i] = (int) (x + (xx - x) * cos - (yy - y) * sin);
            pY[i] = (int) (y + (xx - y) * sin - (yy - y) * cos);
        }
    }

    @Override
    public void scale(int x, int y, float s) {
        for (int i = 0; i < n; i++){
            pX[i] = (int) (pX[i] * s + x * (1 - s));
            pY[i] = (int) (pY[i] * s + y * (1 - s));
        }
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/


}

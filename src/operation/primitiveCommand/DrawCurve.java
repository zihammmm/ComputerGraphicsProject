package operation.primitiveCommand;

import algorithm.CurveAlgorithm;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;
import sample.MyBrush;

import java.util.List;


//曲线
public class DrawCurve extends PrimitiveCommand{
    private int n;

    private String algorithmName;

    /*private int[] pX;   //顶点

    private int[] pY;*/

    private List<Integer> pX;

    private List<Integer> pY;

    private int deltaX;

    private int deltaY;

    private int step;

    public DrawCurve(int i, int nn, List<Integer> xx, List<Integer> yy, String an, Color color){
        super(i, color);
        n = nn;
        algorithmName = an;

        pX = xx;
        pY = yy;

        int mmin = Integer.MAX_VALUE, mmax = Integer.MIN_VALUE;
        for (Integer integer : pX){
            mmin = Math.min(mmin, integer);
            mmax = Math.max(mmax, integer);
        }
        deltaX = mmax - mmin;
        for (Integer integer : pY){
            mmin = Math.min(mmin, integer);
            mmax = Math.max(mmax, integer);
        }
        deltaY = mmax - mmin;
        step = Math.max(deltaX, deltaY);
    }

    public int getN() {
        return n;
    }

    public List<Integer> getpX() {
        return pX;
    }

    public List<Integer> getpY(){
        return pY;
    }

    public void addPoint(int x, int y){
        pX.add(x);
        pY.add(y);
        n++;
        int mmin = Integer.MAX_VALUE, mmax = Integer.MIN_VALUE;
        for (Integer integer : pX){
            mmin = Math.min(mmin, integer);
            mmax = Math.max(mmax, integer);
        }
        deltaX = mmax - mmin;
        for (Integer integer : pY){
            mmin = Math.min(mmin, integer);
            mmax = Math.max(mmax, integer);
        }
        deltaY = mmax - mmin;
        step = Math.max(deltaX, deltaY);
        System.out.println("add new point");
    }

    public int getStep() {
        return step;
    }

    @Override
    public void calculatePoints() {
        if (algorithmName.equals("Bezier"))
            CurveAlgorithm.bezier(this);
        else if (algorithmName.equals("B-spline"))
            CurveAlgorithm.bSpline(this);
        x_min = Integer.MAX_VALUE;
        y_min = Integer.MAX_VALUE;
        y_max = Integer.MIN_VALUE;
        x_max = Integer.MIN_VALUE;
        for (int i = 0; i < x.length; i++){
            x_min = Math.min(x_min, x[i]);
            x_max = Math.max(x_max, x[i]);
            y_min = Math.min(y_min, y[i]);
            y_max = Math.max(y_max, y[i]);
        }
    }

    @Override
    public boolean paint() {
        if (x == null || y == null)
            return false;

        MyBrush.setColor(color);
        for (int i = 0; i < x.length; i++)
            MyBrush.setPixel(x[i], y[i]);
        return true;
    }

    @Override
    public void translate(int dx, int dy) {
        for (int i = 0; i < n; i++){
            pX.set(i, pX.get(i) + dx);
            pY.set(i, pY.get(i) + dy);
            /*pX[i] += dx;
            pY[i] += dy;*/
        }
    }

    @Override
    public void rotate(int x, int y, int r) {
        int xx, yy;
        double cos = Math.cos(r), sin = Math.sin(r);
        for (int i = 0; i < n; i++){
            xx = pX.get(i);
            yy = pY.get(i);
            pX.set(i, (int) (x + (xx - x) * cos - (yy - y) * sin));
            pY.set(i, (int) (y + (xx - y) * sin + (yy - y) * cos));
        }
    }

    @Override
    public void scale(int x, int y, float s) {
        for (int i = 0; i < n; i++){
            pX.set(i, (int) (pX.get(i) * s + x * (1 - s)));
            pY.set(i, (int) (pY.get(i) * s + y * (1 - s)));
            //pY[i] = (int) (pY[i] * s + y * (1 - s));
        }
    }


}

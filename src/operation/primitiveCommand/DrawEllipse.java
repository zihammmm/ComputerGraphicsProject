package operation.primitiveCommand;

import algorithm.ElipseAlgorithm;
import javafx.scene.paint.Color;
import sample.MyBrush;
//椭圆
public class DrawEllipse extends PrimitiveCommand{
    private int x0;

    private int y0;

    private int rx;

    private int ry;

    public DrawEllipse(int i, int xx, int yy, int rrx, int rry, Color color){
        super(i, color);
        x0 = xx;
        y0 = yy;
        rx = rrx;
        ry = rry;
    }

    @Override
    public boolean paint() {
        if (x == null)
            return false;

        MyBrush.setColor(color);
        for (int i = 0; i < x.length; i++)
            MyBrush.setPixel(x[i], y[i]);
        return true;
    }

    @Override
    public void calculatePoints() {
        ElipseAlgorithm.midPointCircle(this);
        x_min = x0 - rx;
        x_max = x0 + rx;
        y_min = y0 - ry;
        y_max = y0 + ry;
    }

    public int getRx() {
        return rx;
    }

    public int getRy() {
        return ry;
    }

    public int getX() {
        return x0;
    }

    public int getY() {
        return y0;
    }

    @Override
    public void translate(int dx, int dy) {
        x0 += dx;
        y0 += dy;
    }

    @Override
    public void rotate(int x, int y, int r) {
        double radians = Math.toRadians(r);
        double cos = Math.cos(radians), sin = Math.sin(radians);

        int xr = x0, yr = y0;
        x0 = (int) Math.round(x + (xr - x) * cos - (yr - y) * sin);
        y0 = (int) Math.round(y + (xr - x) * sin + (yr - y) * cos);
    }

    @Override
    public void scale(int x, int y, float s) {
        x0 = (int) (x0 * s + x * (1 - s));
        y0 = (int) (y0 * s + y * (1 - s));
        rx = (int) (rx * s);
        ry = (int) (ry * s);
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/


}

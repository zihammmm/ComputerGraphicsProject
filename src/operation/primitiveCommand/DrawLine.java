package operation.primitiveCommand;

import algorithm.LineAlgorithm;
import javafx.scene.paint.Color;
import sample.MyBrush;

public class DrawLine extends PrimitiveCommand{
    private int x1;

    private int x2;

    private int y1;

    private int y2;

    private String algorithmName;

    public DrawLine(int i, int xx1, int yy1, int xx2, int yy2, String an, Color color){
        super(i, color);
        x1 = xx1;
        y1 = yy1;
        x2 = xx2;
        y2 = yy2;
        algorithmName = an;

        //System.out.println("x2:"+xx2 + "  " + "y2:" + yy2);
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    @Override
    public void translate(int dx, int dy){
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
    }

    @Override
    public boolean paint() {
        //gc.strokeLine(x1,y1,x2,y2);
        if (x == null || y == null)
            return false;

        MyBrush.setColor(color);
        for (int i = 0; i < x.length; ++i){
            MyBrush.setPixel(x[i], y[i]);
        }
        //System.out.println("point");
        //System.out.println(x[x.length - 1] + "  " + y[y.length - 1]);
        return true;
    }

    @Override
    public void calculatePoints() {
        if (algorithmName.equals("Bresenham")){
            LineAlgorithm.bresenham(this);
        }else if (algorithmName.equals("DDA")){
            LineAlgorithm.dda(this);
        }
        /*x_min = Integer.MAX_VALUE;
        y_min = Integer.MAX_VALUE;
        y_max = Integer.MIN_VALUE;
        x_max = Integer.MIN_VALUE;*/
        x_min = Math.min(x1, x2);
        x_max = Math.max(x1, x2);
        y_min = Math.min(y1, y2);
        y_max = Math.max(y1, y2);
    }

    @Override
    public void rotate(int x, int y, int r) {
        double radians = Math.toRadians(r);
        double cos = Math.cos(radians), sin = Math.sin(radians);

        int xr = x1, yr = y1;
        x1 = (int) Math.round(x + (xr - x) * cos - (yr - y) * sin);
        y1 = (int) Math.round(y + (xr - x) * sin + (yr - y) * cos);

        xr = x2;
        yr = y2;
        x2 = (int) Math.round(x + (xr - x) * cos - (yr - y) * sin);
        y2 = (int) Math.round(y + (xr - x) * sin + (yr - y) * cos);

    }

    @Override
    public void scale(int x, int y, float s) {
        x1 = (int) (x1 * s + x * (1 - s));
        y1 = (int) (y1 * s + y * (1 - s));
        x2 = (int) (x2 * s + x * (1 - s));
        y2 = (int) (y2 * s + y * (1 - s));
    }

}

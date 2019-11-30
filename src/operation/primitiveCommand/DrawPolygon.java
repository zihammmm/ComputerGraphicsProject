package operation.primitiveCommand;

import algorithm.PolygonAlgorithm;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;
import sample.MyBrush;


import java.util.ArrayList;
import java.util.List;

public class DrawPolygon extends PrimitiveCommand {
    private int n;

    private String algorithmName;

    private List<DrawLine> lines;

    private static final int bias = 5;

    public DrawPolygon(int i, Color color){
        super(i, color);
        algorithmName = "Bresenham";
        lines = new ArrayList<>();
        n = 0;
    }

    public DrawPolygon(int i, int nn, String an, int[] xx, int[] yy, Color c){
        super(i, c);

        lines = new ArrayList<>();
        n = nn;
        algorithmName = an;

        for (int j = 0; j < n - 1; j ++){
            lines.add(new DrawLine(j, xx[j], yy[j], xx[j + 1], yy[j + 1], algorithmName, c));
        }
        lines.add(new DrawLine((n - 1), xx[n - 1], yy[n - 1], xx[0], yy[0], algorithmName, c));
    }

    public int getN() {
        return n;
    }

    public DrawLine getLine(int i){
        return lines.get(i);
    }

    public boolean addLine(int x1, int y1, int x2, int y2){
        DrawLine line = null;
        boolean load = false;
        if (n > 1){
            DrawLine drawLine = lines.get(0);
            if (Math.abs(drawLine.getX1() - x2) < bias && Math.abs(drawLine.getY1() - y2) < bias){
                line =new DrawLine(++n, x1, y1, drawLine.getX1(), drawLine.getY1(), algorithmName, color);
                load = true;
            }else
                line = new DrawLine(++n, x1, y1, x2, y2, algorithmName, color);
        }else
            line = new DrawLine(++n, x1, y1, x2, y2, algorithmName, color);
        lines.add(line);
        line.calculatePoints();
        line.paint();
        if (load){
            for (DrawLine line1 : lines){
                x_min = Math.min(line1.getX_min(), x_min);
                y_min = Math.min(line1.getY_min(), y_min);
                x_max = Math.max(line1.getX_max(), x_max);
                y_max = Math.max(line1.getY_max(), y_max);
            }
        }
        return load;
    }

    @Override
    public boolean paint() {
        if (lines.isEmpty())
            return false;

        MyBrush.setColor(color);
        for (DrawLine drawLine : lines)
            drawLine.paint();
        return true;
    }

    @Override
    public void calculatePoints() {
        if (algorithmName.equals("DDA"))
            PolygonAlgorithm.dda(this);
        else if (algorithmName.equals("Bresenham"))
            PolygonAlgorithm.bresenham(this);
    }

    @Override
    public void translate(int dx, int dy) {
        for (DrawLine line : lines)
            line.translate(dx, dy);
    }

    @Override
    public void rotate(int x, int y, int r) {
        for (DrawLine line : lines)
            line.rotate(x, y ,r);
    }

    @Override
    public void scale(int x, int y, float s) {
        for (DrawLine line : lines)
            line.scale(x, y, s);
    }

    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/

}
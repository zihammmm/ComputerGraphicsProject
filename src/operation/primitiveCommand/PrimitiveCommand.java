package operation.primitiveCommand;

import javafx.scene.paint.Color;
import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;
import sample.MyBrush;

import java.util.ArrayList;
import java.util.List;

public abstract class PrimitiveCommand extends MyCommand implements PaintMethod, Transformation {
    private int id;

    protected Integer[] x;

    protected Integer[] y;

    protected int x_min;

    protected int x_max;

    protected int y_min;

    protected int y_max;

    protected Color color;

    public PrimitiveCommand(int i, Color c){
        super(Type.pc);
        color = c;
        id = i;
    }

    public int getId(){
        return id;
    }

    public void setX(List<Integer> x) {
        int size = x.size();
        this.x = new Integer[size];
        x.toArray(this.x);
    }

    public void setY(List<Integer> y) {
        int size = y.size();
        this.y = new Integer[size];
        y.toArray(this.y);
    }

    public int getX_min(){
        return x_min;
    }

    public int getY_max() {
        return y_max;
    }

    public int getX_max() {
        return x_max;
    }

    public int getY_min() {
        return y_min;
    }

    public abstract void calculatePoints();

    public void clearPoints(){
        x = null;
        y = null;
    }

    public Color getColor(){
        return color;
    }

}

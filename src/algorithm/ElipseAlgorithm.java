package algorithm;

import operation.primitiveCommand.DrawEllipse;

import java.util.ArrayList;
import java.util.List;

public class ElipseAlgorithm extends Algorithm{
    public static void midPointCircle(DrawEllipse drawEllipse){
        List<Integer>xx = new ArrayList<>();
        List<Integer>yy = new ArrayList<>();

        int x = drawEllipse.getX(), y = drawEllipse.getY();
        int rx = drawEllipse.getRx(), ry = drawEllipse.getRy();

        int xk = 0, yk = ry;
        double pl = ry * ry - rx * rx * ry + (rx * rx * 1.0) / 4;
        xx.add(xk);
        yy.add(yk);
        while (ry * ry * xk < rx * rx * yk){
            if (pl < 0){
                xx.add(++xk);
                yy.add(yk);
                pl += 2 * ry * ry * xk + ry * ry;
            }else{
                xx.add(++xk);
                yy.add(--yk);
                pl += 2 * ry * ry * xk + ry * ry - 2 * rx * rx * yk;
            }
        }
        pl = ry * ry * (xk + 0.5) * (xk + 0.5) + rx * rx * (y - 1) - rx * rx * ry * ry;
        while (yk != 0){
            if (pl > 0){
                xx.add(xk);
                yy.add(--yk);
                pl += -2 * rx * rx * yk + rx * rx;
            }else{
                xx.add(++xk);
                yy.add(--yk);
                pl += 2 * ry * ry * xk - 2 * rx * rx * yk + rx * rx;
            }
        }

        int length = xx.size();

        int[] array_x = {1, -1, -1};
        int[] array_y = {-1, 1, -1};
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < length; j++){
                xx.add((xx.get(j) * array_x[i]));
                yy.add((yy.get(j) * array_y[i]));
            }
        }
        length = xx.size();
        for (int i = 0; i < length; i++){
            xx.set(i, (xx.get(i) + x));
            yy.set(i, (yy.get(i) + y));
        }
        //load
        drawEllipse.setX(xx);
        drawEllipse.setY(yy);
    }
}

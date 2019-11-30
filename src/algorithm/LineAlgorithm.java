package algorithm;

import javafx.scene.image.PixelWriter;
import operation.primitiveCommand.DrawLine;

import java.util.ArrayList;
import java.util.List;


public class LineAlgorithm extends Algorithm{
    //clip methods
    private static final int TOP = 1 << 3;

    private static final int BOTTOM = 1 << 2;

    private static final int RIGHT = 1 << 1;

    private static final int LEFT = 1;

    public static ClipType CohenSutherland(int x1, int y1, int x2, int y2, DrawLine drawLine){
        int xx1 = drawLine.getX1(), yy1 = drawLine.getY1();
        int xx2 = drawLine.getX2(), yy2 = drawLine.getY2();
        byte c1 = encode(xx1, yy1, x1, y1, x2, y2);
        byte c2 = encode(xx2, yy2, x1, y1, x2, y2);

        if ((c1 & c2) == 0){//两点在不同侧
            if ((c1 | c2) == 0){    //都在窗体内
                return ClipType.inside;
            }
            double k1 = ((yy2 - yy1) * 1.0)/(xx2 - xx1), k2 = ((xx2 - xx1) * 1.0)/(yy2 - yy1);
            while (!((c1 | c2) == 0 || (c1 & c2) != 0)) {   //在窗口内或者在同一侧，跳出循环
                if (c1 != 0) { //交换坐标 p1不在窗口内，保留p2
                    int temp = xx1;
                    xx1 = xx2;
                    xx2 = temp;

                    temp = yy1;
                    yy1 = yy2;
                    yy2 = temp;

                    temp = c1;
                    c1 = c2;
                    c2 = (byte) temp;
                }
                //求交 左右上下
                if ((c2 & LEFT) != 0) {
                    yy2 = (int) Math.round(yy1 + k1 * (x1 - xx1));
                    xx2 = x1;
                }
                if ((c2 & RIGHT) != 0) {
                    yy2 = (int) Math.round(yy1 + k1 * (x2 - xx1));
                    xx2 = x2;
                }
                if ((c2 & TOP) != 0) {
                    xx2 = (int) Math.round(xx1 + k2 * (y1 - yy1));
                    yy2 = y1;
                }
                if ((c2 & BOTTOM) != 0) {
                    xx2 = (int) Math.round(xx1 + k2 * (y2 - yy1));
                    yy2 = y2;
                }
                c1 = encode(xx1, yy1, x1, y1, x2, y2);
                c2 = encode(xx2, yy2, x1, y1, x2, y2);
            }
            drawLine.setX1(xx1);
            drawLine.setY1(yy1);
            drawLine.setX2(xx2);
            drawLine.setY2(yy2);
            if ((c1 | c2) == 0)
                return ClipType.inside;
            else
                return ClipType.outside;
        }else
            return ClipType.outside;
    }

    public static ClipType LiangBarsky(int x1, int y1, int x2, int y2, DrawLine drawLine){
        double u1 = 0, u2 = 1;
        int xx1 = drawLine.getX1(), yy1 = drawLine.getY1();
        int xx2 = drawLine.getX2(), yy2 = drawLine.getY2();
        int delta_x = xx2 - xx1, delta_y = yy2 - yy1;
        int []p = {
                -delta_x, delta_x, -delta_y, delta_y
        };
        int []q = {
                xx1 - x1, x2 - xx1, yy1 - y1, y2 - yy1
        };

        double r;
        for (int i = 0; i < 4; i++){
            if (p[i] == 0 && q[i] < 0)
                return ClipType.outside;
            else {
                r = q[i] * 1.0 / p[i];
                if (p[i] < 0){
                    u1 = Math.max(u1, r);
                }else{
                    u2 = Math.min(u2, r);
                }
                if (u1 > u2){
                    return ClipType.outside;
                }
            }
        }

        drawLine.setX1((int) Math.round (xx1 + delta_x * u1));
        drawLine.setY1((int) Math.round(yy1 + delta_y * u1));
        drawLine.setX2((int) Math.round(xx1 + delta_x * u2));
        drawLine.setY2((int) Math.round(yy1 + delta_y * u2));
        return ClipType.inside;
    }

    //subroutine
    private static byte encode(int x, int y, int x1, int y1, int x2, int y2){
        byte res = 0;
        if (x < x1)
            res |= LEFT;

        if (x > x2)
            res |= RIGHT;

        if (y > y2)
            res |= BOTTOM;

        if (y < y1)
            res |= TOP;

        return res;
    }


    //print methods
    public static void dda(DrawLine drawLine){
        int x1 = drawLine.getX1(), y1 = drawLine.getY1();
        int x2 = drawLine.getX2(), y2 = drawLine.getY2();
        int delta_x = x2 - x1, delta_y = y2 - y1;
        //double k = ((y2 - y1) * 1.0)/(x2 - x1);
        int s;
        List<Integer>x = new ArrayList<>();
        List<Integer>y = new ArrayList<>();

        x.add(x1);
        y.add(y1);
        if (Math.abs(delta_y) > Math.abs(delta_x)){
            s = (y2 - y1)/Math.abs(y2 - y1);
            double xx = x1, k = (delta_x * 1.0) / delta_y;
            while (y1 != y2){
                xx = xx + s * k;
                y1 += s;
                x.add((int)Math.round(xx));
                y.add(y1);
            }
        }else{
            s = (x2 - x1)/Math.abs(x2 - x1);
            double yy = y1, k = (delta_y * 1.0) / delta_x;
            while (x1 != x2){
                yy =  yy + k * s;
                x1 += s;
                x.add(x1);
                y.add((int)Math.round(yy));
            }
        }

        drawLine.setX(x);
        drawLine.setY(y);
    }

    public static void bresenham(DrawLine drawLine){
        int x1 = drawLine.getX1(), y1 = drawLine.getY1();
        int x2 = drawLine.getX2(), y2 = drawLine.getY2();

        boolean sampleByX = false;
        int delta_x = x2 - x1, delta_y = y2 - y1;
        int abs_delta_x = Math.abs(delta_x), abs_delta_y = Math.abs(delta_y);
        int s1 = (delta_x < 0)? -1 : 1, s2 = (delta_y < 0)? -1 : 1;
        if (abs_delta_y < abs_delta_x){
            sampleByX = true;
        }else{
            int temp = abs_delta_x;
            abs_delta_x = abs_delta_y;
            abs_delta_y = temp;
        }

        List<Integer>xx = new ArrayList<>();
        List<Integer>yy = new ArrayList<>();

        int x = x1, y = y1;

        /*
        computing
         */
        xx.add(x);
        yy.add(y);
        int p = 2 * abs_delta_y - abs_delta_x;
        for (int i = 0 ;i < abs_delta_x; ++i){
            if (sampleByX)
                x += s1;
            else
                y += s2;

            if (p < 0){
                xx.add(x);
                yy.add(y);
                p += 2 * abs_delta_y;
            }else{
                if (sampleByX){
                    y += s2;
                }else{
                    x += s1;
                }
                xx.add(x);
                yy.add(y);
                p += 2 * abs_delta_y - 2 * abs_delta_x;
            }
        }

        /*
        loading
         */
        drawLine.setX(xx);
        drawLine.setY(yy);
    }
}

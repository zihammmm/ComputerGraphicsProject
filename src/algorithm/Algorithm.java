package algorithm;

import operation.primitiveCommand.PrimitiveCommand;

public class Algorithm {
    //通用图元算法
    public static void scale(int x, int y, float s, PrimitiveCommand pc){
        pc.scale(x, y, s);
    }

    public static void translate(int dx, int dy, PrimitiveCommand pc){
        pc.translate(dx, dy);
    }

    public static void rotate(int x, int y, int r, PrimitiveCommand pc){
        pc.rotate(x, y, r);
    }
}

package algorithm;

import operation.primitiveCommand.DrawPolygon;

public class PolygonAlgorithm extends Algorithm{
    public static void dda(DrawPolygon drawPolygon){
        int length = drawPolygon.getN();
        for (int i = 0; i < length; i++){
            LineAlgorithm.dda(drawPolygon.getLine(i));
        }
    }

    public static void bresenham(DrawPolygon drawPolygon){
        int length = drawPolygon.getN();
        for (int i = 0; i < length; i++){
            LineAlgorithm.bresenham(drawPolygon.getLine(i));
        }
    }
}

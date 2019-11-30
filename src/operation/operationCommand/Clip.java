package operation.operationCommand;

import algorithm.LineAlgorithm;
import algorithm.ClipType;
import javafx.scene.paint.Color;
import operation.primitiveCommand.DrawLine;
import sample.MyBrush;


public class Clip extends OperationCommand {
    private int x1;

    private int y1;

    private int x2;

    private int y2;

    private String algorithmName;

    public Clip(int i, int xx1, int yy1, int xx2, int yy2, String an){
        super(i);
        x1 = xx1;
        y1 = yy1;
        x2 = xx2;
        y2 = yy2;
        algorithmName = an;
    }


    /*@Override
    public void paint(PixelWriter pixelWriter) {

    }*/

    @Override
    public boolean paint() {
        DrawLine line = null;
        if (pc instanceof DrawLine)
            line = (DrawLine)pc;
        else
            return false;

        ClipType clipType;
        if (algorithmName.equals("Cohen-Sutherland"))
            clipType = LineAlgorithm.CohenSutherland(x1, y1, x2, y2, line);
        else if (algorithmName.equals("Liang-Barsky"))
            clipType = LineAlgorithm.LiangBarsky(x1, y1, x2, y2, line);
        else
            return false;

        if (clipType == ClipType.inside){
            line.calculatePoints();
        }else{
            line.clearPoints();
        }
        return true;
    }

}

package operation.toolCommand;

import javafx.scene.paint.Color;
import operation.MyCommand;
import operation.Type;

public class SetColor extends MyCommand {
    private int R;

    private int G;

    private int B;

    public SetColor(int r, int g, int b){
        super(Type.tc);

        R = r;
        G = g;
        B = b;
    }

    public Color getColor(){
        return Color.rgb(R, G, B);
    }
}

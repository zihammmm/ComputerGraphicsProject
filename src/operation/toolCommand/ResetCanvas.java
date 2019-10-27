package operation.toolCommand;

import operation.MyCommand;
import operation.Type;

public class ResetCanvas extends MyCommand {
    private int width;

    private int height;

    public ResetCanvas(int w, int h){
        super(Type.tc);
        width = w;
        height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}

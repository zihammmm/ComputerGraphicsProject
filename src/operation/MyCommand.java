package operation;

import javafx.scene.image.PixelWriter;

public abstract class MyCommand {
    Type type;

    protected MyCommand(Type t){
        type = t;
    }

    public Type getType(){
        return type;
    }
}



package operation.primitiveCommand;

import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;

public abstract class PrimitiveCommand extends MyCommand implements PaintMethod {
    private int id;

    public PrimitiveCommand(int i){
        super(Type.pc);
        id = i;
    }

    public int getId(){
        return id;
    }
}

package operation.operationCommand;

import operation.MyCommand;
import operation.PaintMethod;
import operation.Type;
import operation.primitiveCommand.PrimitiveCommand;

public abstract class OperationCommand extends MyCommand implements PaintMethod {
    private int id;

    private PrimitiveCommand pc;

    public OperationCommand(int i){
        super(Type.oc);
        id = i;
    }

    public int getId(){
        return id;
    }

    public void setPc(PrimitiveCommand primitiveCommand){
        pc = primitiveCommand;
    }

    protected void check()throws NullPointerException{
        if(pc == null)
            throw new NullPointerException();
    }
}

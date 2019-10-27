package operation.toolCommand;

import operation.MyCommand;
import operation.Type;

public class SaveCanvas extends MyCommand {
    private String Filename;

    public SaveCanvas(String fn){
        super(Type.tc);
        Filename = fn;
    }

    public String getFilename() {
        return Filename;
    }

}

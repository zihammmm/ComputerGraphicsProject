package gui;

import operation.primitiveCommand.PrimitiveCommand;
import sample.GraphSystem;

//只提供参数传递
public class GUI {
    private static GraphSystem graphSystem;

    //private static Tools tools;

    static boolean addPC(int x1, int y1, int x2, int y2, Tools tools){
        switch (tools){
            case line:
                return graphSystem.addPC(x1, y1, x2, y2, Tools.line);
            case curve:
                return graphSystem.addPC(x1, y1, x2, y2, Tools.curve);
            case ellipse:
                return graphSystem.addPC(x1, y1, x2, y2, Tools.ellipse);
            case polygon:
                return graphSystem.addPC(x1, y1, x2, y2, Tools.polygon);
        }
        return false;
    }

    public static void setGraphSystem(GraphSystem graphSystem) {
        GUI.graphSystem = graphSystem;
    }

    public static PrimitiveData getLast(){
        return graphSystem.getLast();
    }

    public static void setLoadingFalse(){
        graphSystem.setLoadingFalse();
    }

    /*static void setTools(Tools t){
        tools = t;
    }*/



}

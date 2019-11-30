package gui;

import javafx.beans.property.SimpleStringProperty;

public class PrimitiveData {
    private final SimpleStringProperty id;

    private final SimpleStringProperty type;

    public PrimitiveData(int i, String t){
        id = new SimpleStringProperty(Integer.toString(i));
        type = new SimpleStringProperty(t);
    }

    public String getId(){
        return id.get();
    }

    public void setId(int i){
        id.set(Integer.toString(i));
    }

    public String getType(){
        return type.get();
    }

    public void setType(String t){
        type.set(t);
    }
}

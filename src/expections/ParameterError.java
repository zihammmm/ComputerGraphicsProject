package expections;

import javafx.scene.control.Alert;

public class ParameterError extends Exception {
    public ParameterError(String str){
        super(str);
    }

    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(getMessage());
        alert.showAndWait();
    }

}

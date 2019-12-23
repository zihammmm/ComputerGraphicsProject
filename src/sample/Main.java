package sample;

import gui.Controller;
import gui.GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

import java.io.File;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        List<String>param = getParameters().getRaw();
        if (!param.isEmpty())
        {
            Canvas canvas = new Canvas(500,500);
            GraphSystem graphSystem = new GraphSystem(canvas);
            graphSystem.openFile(new File(param.get(0)));
            Platform.exit();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            BorderPane borderPane = loader.load();
            primaryStage.setTitle("GraphSystem System");
            Controller controller = loader.getController();
            controller.set(primaryStage);
            Scene scene = new Scene(borderPane);
            scene.getStylesheets().add("sample/background.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


}

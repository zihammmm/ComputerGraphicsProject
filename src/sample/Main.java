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
    private static final int  SCENEWIDTH = 1000;

    private static final int SCENEHEIGHT = 1000;

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
            primaryStage.setTitle("GraphSystem test");
            Controller controller = loader.getController();
            controller.set(primaryStage);
            Scene scene = new Scene(borderPane);
            scene.getStylesheets().add("sample/background.css");
            primaryStage.setScene(scene);
            primaryStage.show();
            /*primaryStage.setTitle("GraphSystem test");
            Group root = new Group();
            BorderPane borderPane = new BorderPane();
            Scene scene = new Scene(root, SCENEWIDTH, SCENEHEIGHT, Color.WHITE);

            MenuBar menuBar = new MenuBar();
            menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

            Menu fileMenu = new Menu("File");
            MenuItem saveMenuItem = new MenuItem("Save");
            MenuItem exitMenuItem = new MenuItem("Exit");
            //MenuItem startMenuItem = new MenuItem("Start");
            exitMenuItem.setOnAction(actionEvent -> Platform.exit());

            fileMenu.getItems().addAll( saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
            menuBar.getMenus().addAll(fileMenu);

            borderPane.setTop(menuBar);
            root.getChildren().add(borderPane);

            //root.getChildren().add(canvas);
            //root.setCenter(canvas);
            borderPane.setCenter(canvas);

            primaryStage.setScene(scene);
            borderPane.prefHeightProperty().bind(scene.heightProperty());
            borderPane.prefWidthProperty().bind(scene.widthProperty());

            primaryStage.show();

            //保存文件
            saveMenuItem.setOnAction(actionEvent -> {
                FileChooser fileChooser1 = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
                fileChooser1.getExtensionFilters().add(extFilter);
                fileChooser1.setTitle("Save");
                File file = fileChooser1.showSaveDialog(primaryStage);
                if (file != null)
                    graphSystem.saveCanvas(file);
            });*/
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


}

package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

import java.io.File;

public class Main extends Application {
    private static final int  SCENEWIDTH = 1000;

    private static final int SCENEHEIGHT = 1000;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("GraphSystem test");
        Group root = new Group();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(root, SCENEWIDTH, SCENEHEIGHT, Color.WHITE);

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("Input");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        //MenuItem startMenuItem = new MenuItem("Start");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        Canvas canvas = new Canvas(500,500);
        Text text = new Text();
        GraphSystem graphSystem = new GraphSystem(canvas, text);


        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(fileMenu);

        borderPane.setTop(menuBar);
        root.getChildren().add(borderPane);

        //root.getChildren().add(canvas);
        //root.setCenter(canvas);
        borderPane.setCenter(canvas);
        borderPane.setLeft(text);

        primaryStage.setScene(scene);
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.show();

        //打开文件
        newMenuItem.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(primaryStage);
            System.out.println(file);
            if (file != null)
                graphSystem.openFile(file);
        });


        //保存文件
        saveMenuItem.setOnAction(actionEvent -> {
            FileChooser fileChooser1 = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
            fileChooser1.getExtensionFilters().add(extFilter);
            fileChooser1.setTitle("Save");
            File file = fileChooser1.showSaveDialog(primaryStage);
            if (file != null)
                graphSystem.saveCanvas(file);
        });

        //启动画图
        /*startMenuItem.setOnAction(actionEvent -> {
            graphSystem.test();
        });*/


    }


    public static void main(String[] args) {
        launch(args);
    }
}

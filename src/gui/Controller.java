package gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.GraphSystem;
import sample.MyBrush;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;


public class Controller {
    private static Tools tools;

    @FXML
    private ToggleGroup toggleGroup1;

    @FXML
    private MenuItem menuItemSave;

    @FXML
    private Menu menuFile;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuItemExit;

    @FXML
    private MenuItem menuItemClear;

    @FXML
    private MenuItem menuItemSetCanvas;

    @FXML
    private Menu menuTool;

    @FXML
    private Menu menuOption;

    @FXML
    private Canvas canvas;

    @FXML
    private TableView<PrimitiveData> tableView;

    @FXML
    private RadioMenuItem radioMenuItemLine;

    @FXML
    private RadioMenuItem radioMenuItemPolygon;

    @FXML
    private RadioMenuItem radioMenuItemEllipse;

    @FXML
    private RadioMenuItem radioMenuItemCurve;

    @FXML
    private RadioMenuItem radioMenuItemTranslate;

    @FXML
    private RadioMenuItem radioMenuItemScale;

    @FXML
    private RadioMenuItem radioMenuItemClip;

    @FXML
    private RadioMenuItem radioMenuItemRotate;


    private GraphSystem graphSystem;

    private Stage primaryStage;

    private int x1, x2;

    private int y1, y2;

    private boolean needMorePoints;   //判断多边形是否完成

    @FXML
    private Text text;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TableColumn<PrimitiveData, String> id;

    @FXML
    private TableColumn<PrimitiveData, String> type;

    private ContextMenu contextMenu;

    private final ObservableList<PrimitiveData> data = FXCollections.observableArrayList(new ArrayList<PrimitiveData>());

    public void set(Stage stage) {
        primaryStage = stage;
    }

    private MenuItem menuItemRotate;
    private MenuItem menuItemScale;
    private MenuItem menuItemTranslate;
    private MenuItem menuItemClip;

    private int currentId;

    private boolean choosePrimi = false;

    private boolean chooseCenter = false;

    private int initX, initY;   //中心

    @FXML
    private void initialize() {
        graphSystem = new GraphSystem(canvas);
        GUI.setGraphSystem(graphSystem);

        menuItemRotate = new MenuItem("旋转");
        menuItemScale = new MenuItem("缩放");
        menuItemTranslate = new MenuItem("平移");
        menuItemClip = new MenuItem("裁剪");

        menuItemRotate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setTitle("旋转");
                textInputDialog.setHeaderText("输入旋转参数:");
                textInputDialog.setContentText("旋转中心(x,y) 顺时针旋转角度");

                Optional<String> result = textInputDialog.showAndWait();
                String str = Integer.toString(currentId);
                result.ifPresent(s -> graphSystem.resloveArgcFromGUI("rotate " + str + " " + s));
            }
        });

        menuItemClip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setTitle("裁剪");
                textInputDialog.setHeaderText("输入裁剪参数:");
                textInputDialog.setContentText("裁剪窗口坐标，裁剪算法");

                Optional<String> result = textInputDialog.showAndWait();
                String str = Integer.toString(currentId);
                result.ifPresent(s -> graphSystem.resloveArgcFromGUI("clip " + str + " " + s));
            }
        });

        menuItemTranslate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setTitle("平移");
                textInputDialog.setHeaderText("输入平移参数:");
                textInputDialog.setContentText("平移向量");

                Optional<String> result = textInputDialog.showAndWait();
                String str = Integer.toString(currentId);
                result.ifPresent(s -> graphSystem.resloveArgcFromGUI("translate " + str + " " + s));
            }
        });

        menuItemScale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setTitle("缩放");
                textInputDialog.setHeaderText("输入缩放参数:");
                textInputDialog.setContentText("缩放中心，缩放倍数");

                Optional<String> result = textInputDialog.showAndWait();
                String str = Integer.toString(currentId);
                result.ifPresent(s -> graphSystem.resloveArgcFromGUI("scale " + str + " " + s));
            }
        });

        tools = null;
        needMorePoints = false;

        tableView.setItems(data);

        contextMenu = new ContextMenu();

        id.setCellValueFactory(new PropertyValueFactory("id"));
        id.setCellFactory(new Callback<TableColumn<PrimitiveData, String>, TableCell<PrimitiveData, String>>() {
            @Override
            public TableCell<PrimitiveData, String> call(TableColumn<PrimitiveData, String> col) {
                final TableCell<PrimitiveData, String> cell = new TableCell<PrimitiveData, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }
                };

                //cell.textProperty().bind(cell.getItem().textProperty());

                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            ContextMenu contextMenu = new ContextMenu();

                            contextMenu.getItems().add(menuItemRotate);
                            contextMenu.getItems().add(menuItemScale);
                            contextMenu.getItems().add(menuItemTranslate);
                            currentId = Integer.parseInt(cell.getText());
                            String type = getTypeFromData(currentId);
                            if (type.equals("直线")) {
                                contextMenu.getItems().add(menuItemClip);
                            }
                            cell.setContextMenu(contextMenu);
                        }
                    }
                });

                return cell;
            }
        });

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY && !needMorePoints) //右键取消
                {
                    setTools(null);
                    choosePrimi = false;
                    currentId = -1;
                    //System.out.println("cancel");
                }

                if (mouseEvent.getButton() == MouseButton.SECONDARY && needMorePoints && tools == Tools.curve){
                    setTools(null);
                    GUI.setLoadingFalse();
                    needMorePoints = false;
                }

                if (tools == Tools.polygon && needMorePoints) {
                    //System.out.println("press");
                    x2 = (int) mouseEvent.getX();
                    y2 = (int) mouseEvent.getY();
                    needMorePoints = GUI.addPC(x1, y1, x2, y2, tools);
                    if (!needMorePoints)
                        data.add(GUI.getLast());
                    //System.out.println(needMorePoints);
                    x1 = x2;
                    y1 = y2;
                    //System.out.println("click");
                }else if (tools == Tools.curve){
                    x2 = (int) mouseEvent.getX();
                    y2 = (int) mouseEvent.getY();
                    //System.out.println(x2 + "  " + y2);
                    GUI.addPC(x1, y1, x2, y2, tools);
                    if (!needMorePoints){
                        data.add(GUI.getLast());
                        needMorePoints = true;
                    }
                }else if (tools == Tools.rotate || tools == Tools.scale || tools == Tools.clip){
                    if (!choosePrimi) {
                        currentId = graphSystem.getIdByCoordinate((int) mouseEvent.getX(), (int) mouseEvent.getY());
                        if (tools == Tools.clip && !data.get(currentId - 1).getType().equals("直线")){
                            currentId = -1;
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("当前图元不支持裁剪");
                            alert.showAndWait();
                        }
                        //System.out.println(currentId);
                        if (currentId != -1)
                            choosePrimi = true;
                        else
                            setTools(null);
                        //System.out.println("no");
                    }else if(!chooseCenter){
                        if (tools == Tools.rotate || tools == Tools.scale){
                            initX = (int) mouseEvent.getX();
                            initY = (int) mouseEvent.getY();
                            chooseCenter = true;
                        }
                        //System.out.println(initX + "  " + initY);
                    }
                }
                mouseEvent.consume();
            }
        });

        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("press");
                //mouseEvent.setDragDetect(false);
                if (tools == Tools.clip) {
                    /*currentId = graphSystem.getIdByCoordinate((int) mouseEvent.getX(), (int) mouseEvent.getY());
                    if (currentId != -1 && data.get(currentId - 1).getType().equals("直线")) {
                        x1 = (int) mouseEvent.getX();
                        y1 = (int) mouseEvent.getY();
                        //System.out.println("clip" + x1 + " " + y1);
                    } else{
                        setTools(null);
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("当前图元不支持裁剪");
                        alert.showAndWait();
                    }*/
                    x1 = (int) mouseEvent.getX();
                    y1 = (int) mouseEvent.getY();
                }else if (mouseEvent.getButton() != MouseButton.SECONDARY && ((tools == Tools.polygon && !needMorePoints)|| tools == Tools.line || tools == Tools.ellipse)) {
                    //System.out.println("press");
                    x1 = (int) mouseEvent.getX();
                    y1 = (int) mouseEvent.getY();
                /*System.out.println("press");
                System.out.println(x1 + "  " + y1);*/
                } else if (tools == Tools.translate || tools == Tools.scale || tools == Tools.rotate) {
                    if (currentId == -1)
                        currentId = graphSystem.getIdByCoordinate((int) mouseEvent.getX(), (int) mouseEvent.getY());

                    if (currentId == -1){
                        setTools(null);
                    }else {
                        if (tools == Tools.translate || tools == Tools.scale || tools == Tools.rotate) {     //平移取当前鼠标坐标
                            x1 = (int) mouseEvent.getX();
                            y1 = (int) mouseEvent.getY();
                        } /*else if (tools == Tools.scale) {    //缩放取左上角
                            initX = graphSystem.getXMin(currentId);
                            initY = graphSystem.getYMin(currentId);
                                *//*x1 = graphSystem.getXMax(currentId);
                                y1 = graphSystem.getYMax(currentId);*//*
                        }*/ /*else if (tools == Tools.rotate) {  //旋转
                            *//*initX = (graphSystem.getXMax(currentId) + graphSystem.getXMin(currentId)) / 2;
                            initY = (graphSystem.getYMax(currentId) + graphSystem.getYMin(currentId)) / 2;*//*
                            x1 = (int) mouseEvent.getX();
                            y1 = (int) mouseEvent.getY();
                            //System.out.println("init" + initX + " " + initY);
                        }*/
                    }
                    mouseEvent.consume();
                }
            }
        });

        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (tools != null) {
                    if (mouseEvent.getButton() != MouseButton.SECONDARY && (tools == Tools.ellipse || tools == Tools.line || (tools == Tools.polygon && !needMorePoints))) {  //多边形第一条边或者 直线 椭圆
                        x2 = (int) mouseEvent.getX();
                        y2 = (int) mouseEvent.getY();
                        GUI.addPC(x1, y1, x2, y2, tools);
                        if (tools == Tools.polygon){
                            needMorePoints = true;
                            x1 = x2;
                            y1 = y2;
                        }else if (tools == Tools.ellipse || tools == Tools.line){
                            data.add(GUI.getLast());
                        }
                        //System.out.println(paintPolygon);
                        //System.out.println("release");
                        mouseEvent.consume();
                    } else if (tools == Tools.clip) {
                        x2 = (int) mouseEvent.getX();
                        y2 = (int) mouseEvent.getY();
                        //System.out.println("clip" + x2 + "  " + y2);
                        graphSystem.clipByGesture(currentId, x1, y1, x2, y2);
                    }
                    //
                /*System.out.println("release");
                System.out.println(tools);
                System.out.println(x2 + "  " + y2);*/
                }
            }
        });

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() != MouseButton.SECONDARY) {
                    x2 = (int) mouseEvent.getX();
                    y2 = (int) mouseEvent.getY();
                    if (tools == Tools.translate) {
                        graphSystem.translateByGesture(currentId, x2 - x1, y2 - y1);
                        x1 = x2;
                        y1 = y2;
                    } else if (tools == Tools.scale) {
                        x2 = (int) mouseEvent.getX();
                        y2 = (int) mouseEvent.getY();
                        int delta_y = y1 - initY, delta_x = x1 - initX;
                        double l1 = Math.sqrt(delta_y * delta_x + delta_x * delta_x);
                        delta_x = x2 - initX;
                        delta_y = y2 - initY;
                        double l2 = Math.sqrt(delta_y * delta_x + delta_x * delta_x);
                        //double l1 = Math.sqrt(y1 * y1 + x1 * x1), l2 = Math.sqrt(y2 * y2 + x2 * x2);
                        float r = (float) ((l2 / l1));
                        graphSystem.scaleByGesture(currentId, initX, initY, r);
                        //System.out.println(r);
                        x1 = x2;
                        y1 = y2;
                    } else if (tools == Tools.rotate) {
                        x2 = (int) mouseEvent.getX();
                        y2 = (int) mouseEvent.getY();
                        double r1 = Math.abs(x1 - initX) * 1.0 / Math.abs(y1 - initY), r2 = Math.abs(x2 - initX) * 1.0 / Math.abs(y2 - initY);
                        double theta = (Math.atan(r2) - Math.atan(r1));
                        if ((x2 - initX) * (x1 - initX) < 0) {
                            theta += Math.PI / 2;
                            theta %= (2 * Math.PI);
                            //System.out.println("123");
                        }
                        theta *= 80;
                        /*initX = (graphSystem.getXMax(currentId) + graphSystem.getXMin(currentId)) / 2;
                        initY = (graphSystem.getYMax(currentId) + graphSystem.getYMin(currentId)) / 2;*/
                        //System.out.println(theta);
                        //System.out.println(initX + "  " + initY);
                        //System.out.println(theta);
                        graphSystem.rotateByGesture(currentId, initX, initY, (int) Math.round(theta));
                        x1 = x2;
                        y1 = y2;
                    }
                    //System.out.println("drag");
                }
            }
        });

        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MyBrush.setColor(colorPicker.getValue());
                //System.out.println(colorPicker.getValue());
            }
        });

    }

    private String getTypeFromData(int id) {
        return data.get(id - 1).getType();
    }

    @FXML
    private void handleExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    private void handleSaveAction(ActionEvent actionEvent) {
        FileChooser fileChooser1 = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
        fileChooser1.getExtensionFilters().add(extFilter);
        fileChooser1.setTitle("Save");
        File file = fileChooser1.showSaveDialog(primaryStage);
        if (file != null)
            graphSystem.saveCanvas(file);
    }

    @FXML
    private void handleSetCanvasAction(ActionEvent actionEvent) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("改变画布大小");
        textInputDialog.setHeaderText("输入宽，长(Width Height):");
        textInputDialog.setContentText("宽，长：");

        Optional<String> result = textInputDialog.showAndWait();
        result.ifPresent(s -> graphSystem.resloveArgcFromGUI("resetCanvas " + s));
    }

    @FXML
    private void handleClearAction(ActionEvent actionEvent) {
        graphSystem.clearCanvas();
        graphSystem.clearMemo();
        data.clear();
        needMorePoints = false;
        setTools(null);
    }

    @FXML
    private void handleLineAction(ActionEvent actionEvent) {
        radioMenuItemLine.setSelected(setTools(Tools.line));
        //GUI.setTools(tools);
    }

    @FXML
    private void handlePolygonAction(ActionEvent actionEvent) {
        radioMenuItemPolygon.setSelected(setTools(Tools.polygon));
        //GUI.setTools(tools);
    }

    @FXML
    private void handleEllipseAction(ActionEvent actionEvent) {
        radioMenuItemEllipse.setSelected(setTools(Tools.ellipse));
        //GUI.setTools(tools);
    }

    @FXML
    private void handleCurveAction(ActionEvent actionEvent) {
        radioMenuItemCurve.setSelected(setTools(Tools.curve));
        //GUI.setTools(tools);
    }

    @FXML
    private void handleTranslateAction(ActionEvent actionEvent) {
        radioMenuItemTranslate.setSelected(setTools(Tools.translate));
    }

    @FXML
    private void handleScaleAction(ActionEvent actionEvent) {
        radioMenuItemScale.setSelected(setTools(Tools.scale));
    }

    @FXML
    private void handleClipAction(ActionEvent actionEvent) {
        radioMenuItemClip.setSelected(setTools(Tools.clip));
    }

    @FXML
    private void handleRotateAction(ActionEvent actionEvent){
        radioMenuItemRotate.setSelected(setTools(Tools.rotate));
    }

    private boolean setTools(Tools t) {
        if (t == null){
            switch (tools){
                case line:radioMenuItemLine.setSelected(false);break;
                case ellipse:radioMenuItemEllipse.setSelected(false);break;
                case curve:radioMenuItemCurve.setSelected(false);break;
                case polygon:radioMenuItemPolygon.setSelected(false);break;
                case scale:radioMenuItemScale.setSelected(false);break;
                case rotate:radioMenuItemRotate.setSelected(false);break;
                case translate:radioMenuItemTranslate.setSelected(false);break;
                case clip:radioMenuItemClip.setSelected(false);break;
            }
        }

        if (tools == t)
            tools = null;
        else
            tools = t;

        if (tools == null) {
            text.setText("当前无工具");
            chooseCenter = false;
            choosePrimi = false;
            return false;
        } else {
            text.setText("当前工具为：" + tools.toString());
            return true;
        }
    }

}

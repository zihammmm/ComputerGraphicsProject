package sample;

import expections.ColorNotFound;
import expections.CommandNotFound;
import expections.NullBrush;
import expections.ParameterError;
import gui.PrimitiveData;
import gui.Tools;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import operation.MyCommand;
import operation.Type;
import operation.operationCommand.OperationCommand;
import operation.primitiveCommand.*;
import operation.toolCommand.ResetCanvas;
import operation.toolCommand.SaveCanvas;
import operation.toolCommand.SetColor;
import reslover.Reslover;

import javax.imageio.ImageIO;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GraphSystem {
    private Canvas canvas;

    private GraphicsContext gc;

    //private File outputFile;
    private Reslover reslover;

    private ArrayList<PrimitiveCommand> memo;

    boolean loading = false;

    public GraphSystem(Canvas c){
        canvas = c;
        gc = c.getGraphicsContext2D();
        memo = new ArrayList<>();
        reslover = new Reslover();
        MyBrush.init(gc);
    }

    /* ---------------------------------------------------------------------------*/

    public boolean read(BufferedReader in){
        String str;
        MyCommand myCommand;
        //int count = 0;
        try {
            while((str = in.readLine()) != null) {
                myCommand = reslover.reslove(str);
                if(myCommand == null)   //drawpolygon drawcurve
                {
                    str = str + " " + in.readLine();
                    myCommand = reslover.reslove(str);
                }
                process(myCommand);
                //count++;
            }
        }catch (IOException | CommandNotFound | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setCanvas(int width, int height) {
        canvas.setHeight(height);
        canvas.setWidth(width);
        gc.clearRect(0, 0, width, height);
    }

    public void clearCanvas(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public boolean openFile(File inputFile){
        try {
            return read(new BufferedReader(new FileReader(inputFile)));
        }catch (FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("File not exists" + e.getMessage());
            return false;
        }
    }

    //两种保存方式

    public boolean saveCanvas(String fileName){
        File outputFile = new File(fileName + ".bmp");
        WritableImage image = canvas.snapshot(new SnapshotParameters(),null);
        try {
            return ImageIO.write(SwingFXUtils.fromFXImage(image,null), "png", outputFile);
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("save successfully");
            alert.showAndWait();*/
        }catch (IOException ex){
            ex.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error occurred while saving the image:" + ex.getMessage());
            alert.showAndWait();
            return false;
        }
    }

    public boolean saveCanvas(File file){
        WritableImage image = canvas.snapshot(new SnapshotParameters(),null);
        try {
            System.out.println(ImageIO.write(SwingFXUtils.fromFXImage(image,null), "png", file));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("save successfully");
            alert.showAndWait();
            return true;
        }catch (IOException ex){
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error occurred while saving the image:" + ex.getMessage());
            alert.showAndWait();
            return false;
        }
    }

    public void process(MyCommand myCommand){
        //检查
        if (myCommand.getType() != Type.tc){
            if(myCommand instanceof OperationCommand) {
                loadShape((OperationCommand)myCommand, ((OperationCommand) myCommand).getId()); //将图元信息加载到oc中
                ((OperationCommand)myCommand).paint();
                rePaint();
            }
            else if (myCommand instanceof PrimitiveCommand){
                try {
                    check();
                } catch (NullBrush | ColorNotFound nullPixelWriter) {
                    nullPixelWriter.printStackTrace();
                }
                memo.add((PrimitiveCommand)myCommand);
                ((PrimitiveCommand)myCommand).calculatePoints();
                ((PrimitiveCommand)myCommand).paint();
            }

        }else{
            String name = myCommand.getClass().getSimpleName();
            switch (name) {
                case "SaveCanvas":
                    saveCanvas(((SaveCanvas) myCommand).getFilename());
                    break;
                case "ResetCanvas":
                    setCanvas(((ResetCanvas) myCommand).getWidth(), ((ResetCanvas) myCommand).getHeight());
                    break;
                case "SetColor":
                    MyBrush.setColor(((SetColor) myCommand).getColor());
                    break;
            }
        }
    }

    /*public void test2(){
        PixelWriter pixelWriter = gc.getPixelWriter();
        for (int i = 100; i < 200; i++)
            pixelWriter.setColor(i,i, Color.BLACK);
    }*/

    private void check() throws NullBrush, ColorNotFound {
        if (MyBrush.colorIsNull())
            throw new ColorNotFound();

        if (MyBrush.pwIsNull())
            throw new NullBrush();
    }

    private boolean loadShape(OperationCommand oc, int i){
        for (PrimitiveCommand pc : memo){
            if(pc.getId() == i){
                oc.setPc(pc);
                return true;
            }
        }
        return false;
    }

    public void rePaint(){
        //clearCanvas
        clearCanvas();

        for (PrimitiveCommand primitiveCommand : memo)
            primitiveCommand.paint();
    }

    public int getIdByCoordinate(int x, int y){
        PrimitiveCommand pc = null;
        for (int i = memo.size() - 1; i >= 0; i--){
            pc = memo.get(i);
            if (x >= pc.getX_min() && x <= pc.getX_max() && y >= pc.getY_min() && y <= pc.getY_max())
                return pc.getId();
        }
        return -1;
    }

    public int getXMin(int id){
        return memo.get(id - 1).getX_min();
    }

    public int getYMin(int id){
        return memo.get(id - 1).getY_min();
    }

    public int getXMax(int id){
        return memo.get(id - 1).getX_max();
    }

    public int getYMax(int id){
        return memo.get(id - 1).getY_max();
    }

    /* -------------------------------------------*/

    public boolean addPC(int x1, int y1, int x2, int y2, Tools tools){
        switch (tools){
            case line:
                DrawLine line = new DrawLine(memo.size() + 1, x1, y1, x2, y2, "Bresenham", MyBrush.getColor());
                memo.add(line);
                line.calculatePoints();
                line.paint();
                break;
            case polygon:
                if (!loading){
                    memo.add(new DrawPolygon(memo.size() + 1, MyBrush.getColor()));
                    loading = true;
                }
                DrawPolygon drawPolygon = (DrawPolygon) memo.get(memo.size() - 1);
                if (drawPolygon.addLine(x1, y1, x2, y2)){
                    loading = false;        //边都加完了
                }
                break;
            case ellipse:       //外接矩形
                int xx = (x2 + x1) / 2, yy = (y2 + y1) / 2;
                int rx = (x2 - x1) / 2, ry = (y2 - y1) / 2;
                DrawEllipse ellipse = new DrawEllipse(memo.size() + 1, xx, yy, rx, ry, MyBrush.getColor());
                memo.add(ellipse);
                ellipse.calculatePoints();
                ellipse.paint();
                break;
            case curve:
                break;
        }
        return loading;
    }

    public PrimitiveData getLast(){
        PrimitiveCommand primitiveCommand = memo.get(memo.size() - 1);
        if (primitiveCommand instanceof DrawLine){
            return new PrimitiveData(primitiveCommand.getId(), "直线");
        }else if (primitiveCommand instanceof DrawEllipse){
            return new PrimitiveData(primitiveCommand.getId(), "椭圆");
        }else if (primitiveCommand instanceof DrawCurve){
            return new PrimitiveData(primitiveCommand. getId(), "曲线");
        }else if (primitiveCommand instanceof DrawPolygon){
            return new PrimitiveData(primitiveCommand.getId(), "多边形");
        }else
            return null;
    }

    public void clearMemo(){
        memo.clear();
    }

    /*--------------------------------------*/
    /*
    接受旋转，平移，裁剪，缩放参数
     */
    public boolean resloveArgcFromGUI(String argc){
        try {
            process(reslover.reslove(argc));
        } catch (CommandNotFound | InvocationTargetException commandNotFound) {
            commandNotFound.printStackTrace();
            return false;
        }
        return true;
    }

    public void clipByGesture(int id, int x1, int y1, int x2, int y2){
        String str = "clip " + id + " " + x1 + " " + y1 + " " + x2 + " " +
                y2 + " Cohen-Sutherland";
        try {
            process(reslover.reslove(str));
        } catch (CommandNotFound | InvocationTargetException commandNotFound) {
            commandNotFound.printStackTrace();
        }
    }

    public void rotateByGesture(int id, int x, int y, int r){
        String str = "rotate " + id + " " + x + " " + y + " " + r;

        try {
            process(reslover.reslove(str));
        } catch (CommandNotFound | InvocationTargetException commandNotFound) {
            commandNotFound.printStackTrace();
        }
    }

    public void scaleByGesture(int id, int x, int y, float s){
        String str = "scale " + id + " " + x + " " + y + " " + s;

        try {
            process(reslover.reslove(str));
        } catch (CommandNotFound | InvocationTargetException commandNotFound) {
            commandNotFound.printStackTrace();
        }
    }

    public void translateByGesture(int id, int dx, int dy){
        String str = "translate " + id + " " + dx + " " + dy;

        try {
            process(reslover.reslove(str));
        } catch (CommandNotFound | InvocationTargetException commandNotFound) {
            commandNotFound.printStackTrace();
        }
    }
}

package sample;

import expections.ColorNotFound;
import expections.CommandNotFound;
import expections.NullPixelWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import operation.MyCommand;
import operation.Type;
import operation.operationCommand.OperationCommand;
import operation.primitiveCommand.DrawLine;
import operation.primitiveCommand.PrimitiveCommand;
import operation.toolCommand.ResetCanvas;
import operation.toolCommand.SaveCanvas;
import operation.toolCommand.SetColor;
import reslover.Reslover;

import javax.imageio.ImageIO;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GraphSystem {
    private Canvas canvas;

    private GraphicsContext gc;

    //private File outputFile;
    private Color color;

    private PixelWriter pixelWriter;

    private Reslover reslover;

    private ArrayList<PrimitiveCommand> memo;

    GraphSystem(Canvas c, Text t){
        canvas = c;
        gc = c.getGraphicsContext2D();
        memo = new ArrayList<>();
    }

    private void setColor(Color color) {
        this.color = color;
        gc.setStroke(color);
    }

    private void setPixelWriter(PixelWriter pixelWriter){
        this.pixelWriter = pixelWriter;
    }

    /* ---------------------------------------------------------------------------*/

    public void setCanvas(int width, int height) {
        canvas.setHeight(height);
        canvas.setWidth(width);

        gc.clearRect(0, 0, width, height);
    }

    public boolean openFile(File inputFile){
        try {
            setPixelWriter(gc.getPixelWriter());
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
            System.out.println(ImageIO.write(SwingFXUtils.fromFXImage(image,null), "png", outputFile));
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("save successfully");
            alert.showAndWait();*/
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

    public boolean read(BufferedReader in){
        String str;
        reslover = new Reslover();
        MyCommand myCommand;
        int count = 0;
        try {
            while((str = in.readLine()) != null) {
                myCommand = reslover.reslove(str);
                if(myCommand == null)   //drawpolygon drawcurve
                {
                    str = str + " " + in.readLine();
                    myCommand = reslover.reslove(str);
                }
                process(myCommand);
                count++;
            }
        } catch (IOException | CommandNotFound | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setContentText("The task is done! The number of commands is " + count);
        alert.showAndWait();
        return true;
    }

    public void process(MyCommand myCommand){
        //检查
        if (myCommand.getType() != Type.tc){
            /*Class<?> mclass = myCommand.getClass();
            Method[] methods = mclass.getMethods();
            for (Method m : methods){
                if(m.getName().equals("paint")){
                    try {
                        m.invoke(myCommand, gc);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }*/
            if(myCommand.getType() == Type.oc) {
                loadShape((OperationCommand)myCommand, ((OperationCommand) myCommand).getId()); //将图元信息加载到oc中
                //测试
                //((OperationCommand) myCommand).paint(gc);
            }
            else if (myCommand.getType() == Type.pc){
                try {
                    check();
                } catch (NullPixelWriter | ColorNotFound nullPixelWriter) {
                    nullPixelWriter.printStackTrace();
                }
                memo.add((PrimitiveCommand)myCommand);
                ((PrimitiveCommand)myCommand).paint(gc);
            }

        }else{
            String name = myCommand.getClass().getSimpleName();
            if (name.equals("SaveCanvas")){
                saveCanvas(((SaveCanvas)myCommand).getFilename());
            }else if(name.equals("ResetCanvas")){
                setCanvas(((ResetCanvas)myCommand).getWidth(), ((ResetCanvas)myCommand).getHeight());
            }else if (name.equals("SetColor")){
                setColor(((SetColor)myCommand).getColor());
            }
        }
    }

    public void test(){
        //processor.setPixelWriter(gc.getPixelWriter());
        setPixelWriter(gc.getPixelWriter());

        for (MyCommand c : memo){
            if (c.getType() == Type.pc || c.getType() == Type.oc)
            {
                process(c);
            } else {
                String name = c.getClass().getName();
                if (name.equals("SaveCanvas")){
                    saveCanvas(((SaveCanvas)c).getFilename());
                }else if(name.equals("ResetCanvas")){
                    setCanvas(((ResetCanvas)c).getWidth(), ((ResetCanvas)c).getHeight());
                }else if (name.equals("setColor")){
                    setColor(((SetColor)c).getColor());
                }
            }
        }
    }

    /*public void test2(){
        PixelWriter pixelWriter = gc.getPixelWriter();
        for (int i = 100; i < 200; i++)
            pixelWriter.setColor(i,i, Color.BLACK);
    }*/

    public void check() throws NullPixelWriter, ColorNotFound {
        if (color == null)
            throw new ColorNotFound();

        if (pixelWriter == null)
            throw new NullPixelWriter();
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
}

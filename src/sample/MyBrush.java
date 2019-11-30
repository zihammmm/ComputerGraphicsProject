package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class MyBrush {
    private static Color color;

    private static PixelWriter pixelWriter;

    private static GraphicsContext graphicsContext;

    public static void init(GraphicsContext gc){
        pixelWriter = gc.getPixelWriter();
        graphicsContext = gc;
        color = Color.BLACK;
    }

    public static void setColor(Color c) {
        color = c;
        graphicsContext.setStroke(c);
    }

    public static Color getColor(){
        return color;
    }

    public static boolean colorIsNull(){
        return color == null;
    }

    public static boolean pwIsNull(){
        return pixelWriter == null;
    }

    public static void setPixel(int x, int y){
        pixelWriter.setColor(x, y, color);
    }

}

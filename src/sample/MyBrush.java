package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class MyBrush {
    private Color color;

    private PixelWriter pixelWriter;

    MyBrush(GraphicsContext graphicsContext){
        this.pixelWriter = graphicsContext.getPixelWriter();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean colorIsNull(){
        return color == null;
    }

    public boolean pwIsNull(){
        return pixelWriter == null;
    }

}

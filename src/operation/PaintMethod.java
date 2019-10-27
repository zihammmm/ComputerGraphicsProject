package operation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

public interface PaintMethod {
    //void paint(PixelWriter pixelWriter);

    boolean paint(GraphicsContext gc);
}

package operation.primitiveCommand;

public interface Transformation {

    void translate(int dx, int dy);

    void rotate(int x, int y, int r);

    void scale(int x, int y, float s);

}

package reslover;

import expections.CommandNotFound;
import expections.ParameterError;
import javafx.scene.control.Alert;
import operation.primitiveCommand.*;
import operation.operationCommand.*;
import operation.toolCommand.*;
import operation.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reslover implements ResloverMethod{
    private final int maxWidthAndHeight = 1000;

    private final int minWidthAndHeight = 100;

    public MyCommand reslove(String cmd) throws CommandNotFound, IllegalArgumentException, InvocationTargetException   //解析语句
    {
        String[] ss=cmd.split("\\s+");
        CommandType c;
        c=fitter(ss[0]);
        //String c = ss[0];

        Class<?> mclass = this.getClass();
        Method[] methods = mclass.getDeclaredMethods();
        try{
            for (Method m : methods){
                if (m.getName().equals(c.toString())){
                    return (MyCommand)m.invoke(this, (Object) ss);        //利用反射调用对应的方法，解析语句，封装为类
                }
            }
        }catch (IllegalArgumentException | IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException ex){
            Throwable cause = ex.getCause();
            if(cause instanceof ParameterError){
                System.err.println("捕获到参数异常");
                ((ParameterError)cause).printStackTrace();

                //弹出提示框
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Parameter Error");
                alert.setContentText("Please check the parameter range");
                alert.showAndWait();
            }
        }
        return null;
    }

    private CommandType fitter(String cmd)throws CommandNotFound{
        for (CommandType c: CommandType.values()){
            if (c.toString().equals(cmd))
                return c;
        }
        throw new CommandNotFound(cmd);
    }

    @Override
    public DrawLine drawLine(String[] cmd) {
        int[] array = new int[5];
        try {
            for (int i =0; i < 5; ++i){
                array[i] = Integer.parseInt(cmd[i+1]);
            }
        }catch (NumberFormatException e){
            /*
            格式错误
             */
            e.printStackTrace();
            return null;
        }
        return new DrawLine(array[0], array[1], array[2], array[3], array[4], cmd[6]);
    }

    @Override
    public ResetCanvas resetCanvas(String[] cmd) throws ParameterError{
        int w,h;
        try {
            w = Integer.parseInt(cmd[1]);
            h = Integer.parseInt(cmd[2]);
        }catch (NumberFormatException nfe){
            /*
            格式错误
             */
            nfe.printStackTrace();
            return null;
        }
        if (!(w <= maxWidthAndHeight && w >= minWidthAndHeight))
            throw new ParameterError();

        if(!(h <= maxWidthAndHeight && w >= minWidthAndHeight))
            throw new ParameterError();

        return new ResetCanvas(w,h);
    }

    @Override
    public SaveCanvas saveCanvas(String[] cmd) {
        return new SaveCanvas(cmd[1]);
    }

    @Override
    public Rotate rotate(String[] cmd) {
        int id, dx, dy, r;

        try {
            id = Integer.parseInt(cmd[1]);
            dx = Integer.parseInt(cmd[2]);
            dy = Integer.parseInt(cmd[3]);
            r = Integer.parseInt(cmd[4]);
        }catch (NumberFormatException e){
            /*
            格式错误
             */
            return null;
        }
        return new Rotate(id, dx, dy, r);
    }

    @Override
    public Scale scale(String[] cmd) {
        int id, x, y;
        float s;

        try {
            id = Integer.parseInt(cmd[1]);
            x = Integer.parseInt(cmd[2]);
            y = Integer.parseInt(cmd[3]);
            s= Float.parseFloat(cmd[4]);
        }catch (NumberFormatException e){
            /*
            格式错误
             */
            return null;
        }
        return new Scale(id, x, y, s);
    }

    @Override
    public Clip clip(String[] cmd) {
        int id, x1, y1, x2, y2;

        try {
            id = Integer.parseInt(cmd[1]);
            x1 = Integer.parseInt(cmd[2]);
            y1 = Integer.parseInt(cmd[3]);
            x2 = Integer.parseInt(cmd[4]);
            y2 = Integer.parseInt(cmd[5]);
        }catch (NumberFormatException e){
            /*
            格式错误
             */
            return null;
        }
        return new Clip(id, x1, y1, x2, y2, cmd[6]);
    }

    @Override
    public Translate translate(String[] cmd) {
        int id, dx, dy;

        try {
            id = Integer.parseInt(cmd[1]);
            dx = Integer.parseInt(cmd[2]);
            dy = Integer.parseInt(cmd[3]);
        }catch (NumberFormatException e){
            /*
            格式错误
             */
            return null;
        }
        return new Translate(id, dx, dy);
    }

    @Override
    public SetColor setColor(@NotNull String[] cmd) throws ParameterError {
        int r, g, b;
        try {
            r = Integer.parseInt(cmd[1]);
            g = Integer.parseInt(cmd[2]);
            b = Integer.parseInt(cmd[3]);
        }catch (NumberFormatException e){
            /*
            格式错误
             */
            e.printStackTrace();
            return null;
        }

        if (r < 0 || r > 255)
            throw new ParameterError();

        if (g < 0 || g > 255)
            throw new ParameterError();

        if (b < 0 || b > 255)
            throw new ParameterError();

        return new SetColor(r,g,b);
    }

    @Override
    public DrawPolygon drawPolygon(String[] cmd) {
        if (cmd.length <= 4){
            return null;
        }
        int id, n;
        try {
            id = Integer.parseInt(cmd[1]);
            n = Integer.parseInt(cmd[2]);
            int[] x = new int [n];
            int[] y = new int [n];
            for (int i = 0, j = 4; i < n; i++, j+=2){
                x[i] = Integer.parseInt(cmd[j]);
                y[i] = Integer.parseInt(cmd[j + 1]);
            }
            return new DrawPolygon(id, n, cmd[3], x, y);
        }catch (NumberFormatException e){
            /*
            格式错误
             */
            return null;
        }
    }

    @Override
    public DrawEllipse drawEllipse(String[] cmd) {
        int id, x, y, rx, ry;

        try {
            id = Integer.parseInt(cmd[1]);
            x = Integer.parseInt(cmd[2]);
            y = Integer.parseInt(cmd[3]);
            rx = Integer.parseInt(cmd[4]);
            ry = Integer.parseInt(cmd[5]);
        }catch (NumberFormatException e){
            /*
            格式错误
             */
            return null;
        }
        return new DrawEllipse(id, x, y, rx, ry);
    }

    @Override
    public DrawCurve drawCurve(String[] cmd) {
        if (cmd.length <= 4){
            return null;
        }

        int id, n;
        try {
            id = Integer.parseInt(cmd[1]);
            n = Integer.parseInt(cmd[2]);
            int[] x = new int[n];
            int[] y = new int[n];
            for (int i = 0, j = 4; i < n; i++, j+=2){
                x[i] = Integer.parseInt(cmd[j]);
                y[i] = Integer.parseInt(cmd[j + 1]);
            }
            return new DrawCurve(id, n, x, y, cmd[3]);
        }catch (NumberFormatException e){
            /*
            格式错误
             */
            return null;
        }
    }
}

package algorithm;

import operation.primitiveCommand.DrawCurve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CurveAlgorithm extends Algorithm{

    public static void bezier(DrawCurve drawCurve){
        int n = drawCurve.getN() - 1;

        long[] factorial = getFactorial(n);

        List<Integer> xx = new ArrayList<>();
        List<Integer> yy = new ArrayList<>();
        double step = 1.0 / (2 * drawCurve.getStep());
        for (double u = 0; u <= 1; u += step){
            double u1 = 1 - u;
            double x = 0, y = 0;
            for (int i = 0; i <= n; i++){
                long ration = getRation(factorial, n, i);
                x = x + ration*drawCurve.getpX().get(i)*Math.pow(u1, n - i)*Math.pow(u, i);
                y = y + ration*drawCurve.getpY().get(i)*Math.pow(u1, n - i)*Math.pow(u, i);
            }
            xx.add((int) Math.round(x));
            yy.add((int) Math.round(y));
        }
        drawCurve.setX(xx);
        drawCurve.setY(yy);
    }

    //3次均匀
    public static void bSpline(DrawCurve drawCurve){
        int n = drawCurve.getN(), k = 3;
        double[] nodeVector = createKnots(n, k);

        System.out.println(Arrays.toString(nodeVector));
        List<Integer>xx = new ArrayList<>();
        List<Integer>yy = new ArrayList<>();

        List<Integer> px = drawCurve.getpX();
        List<Integer> py = drawCurve.getpY();


        double step = 1.0 / (2 *drawCurve.getStep());
        double[] N = new double[n + k];
        for (double u = 0; u <= 1; u += step){
            double x = 0, y = 0;
            for (int i = 0; i < n + k; i++){
                if (nodeVector[i] <= u && nodeVector[i + 1] > u)
                    N[i] = 1;
                else
                    N[i] = 0;
            }
            for (int i = 1; i <= k; i++){
                for (int j = 0; j < n + k - i ;j++){
                    double a = nodeVector[j + i] - nodeVector[j];
                    double b = nodeVector[j + i + 1] - nodeVector[j + 1];
                    a = (a != 0) ? (u - nodeVector[j]) / a : 0;
                    b = (b != 0) ? (nodeVector[j + i + 1] - u) / b : 0;
                    N[j] = a * N[j] + b * N[j + 1];
                }
            }
            for (int i = 0; i < n; i++){
                x += N[i] * px.get(i);
                y += N[i] * py.get(i);
            }
            xx.add((int) Math.round(x));
            yy.add((int) Math.round(y));
        }

        drawCurve.setX(xx);
        drawCurve.setY(yy);

    }

    //subfunction
    private static long[] getFactorial(int n){
        long[] array = new long[n + 1];
        array[0] = 0;
        array[1] = 1;
        for (int i = 2; i <= n; i++){
            array[i] = array[i - 1] * i;
        }
        return array;
    }

    private static long getRation(long[] array, int n, int i){
        i = Math.min(i, n - i);
        if (i == 0 || i == n)
            return 1;
        else
            return array[n]/(array[i] * array[n - i]);
    }

    private static double[] createKnots(int n, int k){
        int m = n + k + 1;

        double[] knots = new double[m];
        int i;
        double step = 1.0 / (m - 2 * k - 1);
        for (i = k; i < m - k; i++){
            knots[i] = step * (i - k);
        }
        while (i < m){
            knots[i++] = 1;
        }
        return knots;
    }
}

package Math;

public class MathFunctions {
    public static double sigmoid(double x)
    {
        return 1 / (1 + Math.exp(-x));
    }

    public static double sigmoidDerivative(double x)
    {
        double sigmoid = sigmoid(x);
        return sigmoid * (1 - sigmoid);
    }

    public static double reLu(double x)
    {
        if(x > 0) {
            return x;
        }
        else {
            return 0.0;
        }
    }

    public static double reLuDerivative(double x)
    {
        if(x > 0) {
            return 1;
        }
        else {
            return 0.0;
        }
    }
}

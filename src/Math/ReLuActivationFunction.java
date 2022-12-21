package Math;

import java.io.Serializable;

public class ReLuActivationFunction implements  ActivationFunction, Serializable {
    @Override
    public double activationFunction(double value) {
        return MathFunctions.reLu(value);
    }

    @Override
    public double activationFunctionDerivative(double value) {
        return MathFunctions.reLuDerivative(value);
    }
}

package Math;

import java.io.Serializable;

public class SigmoidActivationFunction implements ActivationFunction{

    @Override
    public double activationFunction(double value) {
        return MathFunctions.sigmoid(value);
    }

    @Override
    public double activationFunctionDerivative(double value) {
        return MathFunctions.sigmoidDerivative(value);
    }
}

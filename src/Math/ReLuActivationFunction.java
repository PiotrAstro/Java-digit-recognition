package Math;


public class ReLuActivationFunction implements  ActivationFunction{
    @Override
    public double activationFunction(double value) {
        return MathFunctions.reLu(value);
    }

    @Override
    public double activationFunctionDerivative(double value) {
        return MathFunctions.reLuDerivative(value);
    }
}

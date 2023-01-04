package Math;

public class LeakReLuActivationFunction implements ActivationFunction{
        @Override
        public double activationFunction(double value) {
            return MathFunctions.leakReLu(value);
        }

        @Override
        public double activationFunctionDerivative(double value) {
            return MathFunctions.leakReLuDerivative(value);
        }
}

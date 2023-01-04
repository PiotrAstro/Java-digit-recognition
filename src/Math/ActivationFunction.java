package Math;

import java.io.Serializable;

public interface ActivationFunction extends Serializable {
    public double activationFunction(double value);
    public double activationFunctionDerivative(double value);
}

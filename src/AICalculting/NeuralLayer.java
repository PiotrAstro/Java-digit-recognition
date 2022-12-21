package AICalculting;

import MatrixCalculations.MatrixDouble;
import MatrixCalculations.MatrixDouble2d;
import Math.*;

import java.io.Serializable;
import java.util.Random;

public class NeuralLayer implements Serializable {
    private static final long serialVersionUID = 2;


    private final double initialWeightsRange = 0.6;
    private final double initialWeightsStart = 0;
    private final double initialBiasRange = 0;
    private final double initialBiasStart = 1;

    private double [][] weights;
    private double [] biases;
    private double [] lastFinalCurrentNeurons; // last indicates that it was created during last calculation of output, current means that these are neurons of this layer, this layer is sum after using activation function
    private double [] lastSummedCurrentNeuronsWeightsAndBiases; // this is sum before using activation function
    private double [] lastCurrentNeuronsDerivatives; // this layers neurons derivatives
    private double [][] sumOfWeightsDerivativesInChunk;
    private double [] sumOfBiasesDerivativesInChunk;
    private int activationLength;
    private int neuronsNumber;
    private ActivationFunction activationFunction;

    public NeuralLayer(int activationLength, int neuronsNumber, ActivationFunction activationFunction) {
        this.activationLength = activationLength;
        this.neuronsNumber = neuronsNumber;
        this.activationFunction = activationFunction;

        lastFinalCurrentNeurons = new double[this.neuronsNumber];
        lastSummedCurrentNeuronsWeightsAndBiases = new double[this.neuronsNumber];
        lastCurrentNeuronsDerivatives = new double[this.neuronsNumber];
        sumOfWeightsDerivativesInChunk = new double[this.neuronsNumber][this.activationLength];
        sumOfBiasesDerivativesInChunk = new double[this.neuronsNumber];

        MatrixDouble2d.setZero(sumOfWeightsDerivativesInChunk);
        MatrixDouble.setZero(sumOfBiasesDerivativesInChunk);

        weights = generateRandomWeights();
        biases = generateRandomBiases();
    }

    public NeuralLayer() {
        this.activationLength = 0;
        this.neuronsNumber = 0;
    }


    //output

    public double [] calculateOutput(double[] activationTable) {
        if(activationTable.length == activationLength) {
            double[] result = MatrixDouble2d.multiplyMatrix2dBy1d(weights, activationTable);
            MatrixDouble.add(result, biases);

            lastSummedCurrentNeuronsWeightsAndBiases = MatrixDouble.copyTable(result);

            MatrixDouble.applyActivationFunction(result, activationFunction);
            lastFinalCurrentNeurons = result;

            return result;
        }
        else {
            return activationTable;
        }
    }


    //learning
    public void learnStep(double[] activation, double[][] nextWeights, double[] nextSummedWeightsBias, double[] nextActivationDerivatives) {
        calculateLearningCurrentNeuronsDerivatives(nextWeights, nextSummedWeightsBias, nextActivationDerivatives);
        learnStepCalculateBiasWeights(activation);
    }

    public void learnStepLastLayer(double[] activation, int correctResult) {
        lastLayerCalculateLearningCurrentNeuronsDerivatives(correctResult);

        learnStepCalculateBiasWeights(activation);
    }

    public void applyLearningChunk(int learningImagesChunkNumber, double learningRate) {
        for(int i = 0; i < weights.length; i++) {
            biases[i] += sumOfBiasesDerivativesInChunk[i] / learningImagesChunkNumber * learningRate;
            for(int k = 0; k < weights[i].length; k++) {
                weights[i][k] += sumOfWeightsDerivativesInChunk[i][k] / learningImagesChunkNumber * learningRate;
            }
        }

        MatrixDouble2d.setZero(sumOfWeightsDerivativesInChunk);
        MatrixDouble.setZero(sumOfBiasesDerivativesInChunk);
    }

    public void learnStepCalculateBiasWeights(double[] activation) {
        double tempDerivativeSummedWeightsBiasMultiplyCurrentNeuronDerivative;

        for(int i = 0; i < weights.length; i++) {
            tempDerivativeSummedWeightsBiasMultiplyCurrentNeuronDerivative = activationFunction.activationFunctionDerivative(lastSummedCurrentNeuronsWeightsAndBiases[i]) * lastCurrentNeuronsDerivatives[i];

            sumOfBiasesDerivativesInChunk[i] += tempDerivativeSummedWeightsBiasMultiplyCurrentNeuronDerivative;


            for(int k = 0; k < weights[i].length; k++) {
                sumOfWeightsDerivativesInChunk[i][k] += activation[k] * tempDerivativeSummedWeightsBiasMultiplyCurrentNeuronDerivative;
            }
        }
    }


    public void calculateLearningCurrentNeuronsDerivatives(double[][] nextWeights, double[] nextSummedWeightsBias, double[] nextActivationDerivatives) {
        double singleDerivative;

        for(int i = 0; i < lastCurrentNeuronsDerivatives.length; i++) {
            singleDerivative = 0;
            for(int k = 0; k < nextSummedWeightsBias.length; k++) {
                singleDerivative += nextWeights[k][i] * activationFunction.activationFunctionDerivative(nextSummedWeightsBias[k]) * nextActivationDerivatives[k];
            }
            lastCurrentNeuronsDerivatives[i] = singleDerivative;
        }
    }

    public void lastLayerCalculateLearningCurrentNeuronsDerivatives(int correctResult) {
        int correctOutputHere;
        for(int i = 0; i < lastCurrentNeuronsDerivatives.length; i++) {
            correctOutputHere = (correctResult == i ? 1 : 0);
            lastCurrentNeuronsDerivatives[i] = 2 * (correctOutputHere - lastFinalCurrentNeurons[i]);
        }
    }

    //other
    public String generateSaveString() {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(activationLength);
        sBuilder.append(" ");
        sBuilder.append(neuronsNumber);
        sBuilder.append("\n");
        sBuilder.append(MatrixDouble.generateSaveString(biases));
        sBuilder.append(MatrixDouble2d.generateSaveString(weights));

        return sBuilder.toString();
    }

    public double[][] generateRandomWeights() {
        double[][] result = new double[neuronsNumber][activationLength];
        Random random = new Random();

        for(int i = 0; i < result.length; i++) {
            for(int k = 0; k < result[i].length; k++) {
                result[i][k] = initialWeightsStart + -initialWeightsRange + random.nextDouble() * initialWeightsRange * 2;
            }
        }

        return result;
    }

    public double[] generateRandomBiases() {
        double[] result = new double[neuronsNumber];
        Random random = new Random();

        for(int i = 0; i < result.length; i++) {
            result[i] = initialBiasStart - initialBiasRange + random.nextDouble() * initialBiasRange * 2;
        }

        return result;
    }

    public int getActivationLength() {
        return activationLength;
    }

    public void setActivationLength(int activationLength) {
        this.activationLength = activationLength;
    }

    public int getNeuronsNumber() {
        return neuronsNumber;
    }

    public void setNeuronsNumber(int neuronsNumber) {
        this.neuronsNumber = neuronsNumber;
    }

    public double[] getLastFinalCurrentNeurons() {
        return lastFinalCurrentNeurons;
    }

    public void setLastFinalCurrentNeurons(double[] lastFinalCurrentNeurons) {
        this.lastFinalCurrentNeurons = lastFinalCurrentNeurons;
    }

    public double[] getLastSummedCurrentNeuronsWeightsAndBiases() {
        return lastSummedCurrentNeuronsWeightsAndBiases;
    }

    public void setLastSummedCurrentNeuronsWeightsAndBiases(double[] lastSummedCurrentNeuronsWeightsAndBiases) {
        this.lastSummedCurrentNeuronsWeightsAndBiases = lastSummedCurrentNeuronsWeightsAndBiases;
    }

    public double[] getLastCurrentNeuronsDerivatives() {
        return lastCurrentNeuronsDerivatives;
    }

    public void setLastCurrentNeuronsDerivatives(double[] lastCurrentNeuronsDerivatives) {
        this.lastCurrentNeuronsDerivatives = lastCurrentNeuronsDerivatives;
    }

    public double[][] getSumOfWeightsDerivativesInChunk() {
        return sumOfWeightsDerivativesInChunk;
    }

    public void setSumOfWeightsDerivativesInChunk(double[][] sumOfWeightsDerivativesInChunk) {
        this.sumOfWeightsDerivativesInChunk = sumOfWeightsDerivativesInChunk;
    }

    public double[][] getWeights() {
        return weights;
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }

    public double[] getBiases() {
        return biases;
    }

    public void setBiases(double[] biases) {
        this.biases = biases;
    }


}

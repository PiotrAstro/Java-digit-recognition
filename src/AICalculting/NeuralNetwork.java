package AICalculting;

import MainActions.MainActions;
import MatrixCalculations.MatrixDouble;
import Math.*;

import java.io.BufferedReader;
import java.io.Serializable;

public class NeuralNetwork implements Serializable {
    private static final long serialVersionUID = 2;


    private double learningRate = 0.4;
    private ActivationFunction normalLayerActivationFunction = new SigmoidActivationFunction();
    private ActivationFunction lastLayerActivationFunction = new SigmoidActivationFunction();
    private int imageSize = MainActions.getImageSize();
    private int learningImagesChunkNumber = 10;
    private final int LayersNumber = 3;
    private final int normalLayerNeuronsNumber = 100;
    private final int lastLayerNeuronsNumber = 10;
    private NeuralLayer[] layers;


    public NeuralNetwork() {
        layers = new NeuralLayer[LayersNumber];
        int activationSize = imageSize;
        int neuronsNumber = normalLayerNeuronsNumber;
        ActivationFunction activationFunctionHere = normalLayerActivationFunction;

        for(int i = 0; i < LayersNumber; i++) {
            if(i == LayersNumber - 1) {
                neuronsNumber = lastLayerNeuronsNumber;
                activationFunctionHere = lastLayerActivationFunction;
            }

            layers[i] = new NeuralLayer(activationSize, neuronsNumber, activationFunctionHere);

            activationSize = normalLayerNeuronsNumber;
        }
    }

    public String generateSaveString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(imageSize);
        sBuilder.append(" ");
        sBuilder.append(LayersNumber);
        sBuilder.append(" ");
        sBuilder.append(normalLayerNeuronsNumber);
        sBuilder.append(" ");
        sBuilder.append(lastLayerNeuronsNumber);
        sBuilder.append("\n\n");

        for(int i = 0; i < LayersNumber; i++) {
            sBuilder.append(layers[i].generateSaveString());
            sBuilder.append("\n\n");
        }

        return sBuilder.toString();
    }

    //learning methods
    public void learnImages(double[][] images, int[] labels) {
        for(int i = 0; i < labels.length; i += learningImagesChunkNumber) {
            learnChunkOfImages(images, labels, i);
        }
    }

    public void learnImages(double[][] images, int[] labels, int whereToStart, int howMuch) {
        for(int i = whereToStart; i < whereToStart + howMuch && i < labels.length; i += learningImagesChunkNumber) {
            learnChunkOfImages(images, labels, i);
        }
    }

    public void learnChunkOfImages(double[][] images, int[] labels, int startImage) {
        int currentImage;

        for(int i = 0; i < learningImagesChunkNumber; i++) {
            currentImage = i + startImage;

            calculateResultNumber(images[currentImage]);

            int currentLayerNumber = layers.length - 1;

            layers[currentLayerNumber].learnStepLastLayer(
                    layers[currentLayerNumber - 1].getLastFinalCurrentNeurons(),
                    labels[currentImage]);

            currentLayerNumber --;

            for(; currentLayerNumber >= 1; currentLayerNumber--) {
                layers[currentLayerNumber].learnStep(
                        layers[currentLayerNumber - 1].getLastFinalCurrentNeurons(),
                        layers[currentLayerNumber + 1].getWeights(),
                        layers[currentLayerNumber + 1].getLastSummedCurrentNeuronsWeightsAndBiases(),
                        layers[currentLayerNumber + 1].getLastCurrentNeuronsDerivatives());
            }

            layers[currentLayerNumber].learnStep(
                    images[currentImage],
                    layers[currentLayerNumber + 1].getWeights(),
                    layers[currentLayerNumber + 1].getLastSummedCurrentNeuronsWeightsAndBiases(),
                    layers[currentLayerNumber + 1].getLastCurrentNeuronsDerivatives());
        }

        for(int currentLayerNumber = 0; currentLayerNumber < layers.length; currentLayerNumber++) {
            layers[currentLayerNumber].applyLearningChunk(learningImagesChunkNumber, learningRate);
        }
    }


    //calculating normal outcome
    public double[] calculateResultTable(double[] imageTable) {
        double[] result = MatrixDouble.copyTable(imageTable);

        for(int i = 0; i < LayersNumber; i++) {
            result = layers[i].calculateOutput(result);
        }

        return result;
    }

    public int calculateResultNumber(double[] imageTable) {
        int result = 0;
        double[] resultTable = calculateResultTable(imageTable);

        for(int i = 0; i < lastLayerNeuronsNumber; i++) {
            if(resultTable[result] < resultTable[i]) {
                result = i;
            }
        }

        return result;
    }


    //getters and setters
    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public int getLearningImagesChunkNumber() {
        return learningImagesChunkNumber;
    }

    public void setLearningImagesChunkNumber(int learningImagesChunkNumber) {
        this.learningImagesChunkNumber = learningImagesChunkNumber;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
}

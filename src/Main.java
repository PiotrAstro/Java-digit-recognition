import AICalculting.NeuralNetwork;
import FileHandling.FileHandling;
import MainActions.MainActions;
import MatrixCalculations.MatrixDouble2d;
import PrintingAndDrawing.Drawing;
import PrintingAndDrawing.PrintingNormalText;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String neuralNetworkFilename = "neuralNetworkSave_v003.ser";


        //MainActions mainActions = new MainActions(new NeuralNetwork());
        MainActions mainActions = new MainActions(neuralNetworkFilename);


        //double[][] trainImages = FileHandling.loadChunkOfImages(42000, "train_correct.txt");
        //int[] trainLabels = FileHandling.loadChunkOfImageLabels(42000, "train_labels.txt");

        double[][] testImages = FileHandling.loadChunkOfImages(2000, "test.csv");

        //mainActions.learnImages(trainImages, trainLabels);
        //mainActions.saveNeuralNetworkToFile(neuralNetworkFilename);


        //mainActions.liveTimeLearning(trainImages, trainLabels, 2000);
        mainActions.showNumbersOnScreenAndGuess(testImages,700);


    }
}
import FileHandling.FileHandling;
import MainActions.MainActions;
import AICalculting.*;

public class Main {
    public static void main(String[] args) {
        String neuralNetworkFilename = "neuralNetworkSave_v005.ser";

        MainActions mainActions = new MainActions(new NeuralNetwork());
        //MainActions mainActions = new MainActions(neuralNetworkFilename);


        double[][] trainImages = FileHandling.loadChunkOfImages(42000, "Java-digit-recognition/train_correct.txt");
        int[] trainLabels = FileHandling.loadChunkOfImageLabels(42000, "Java-digit-recognition/train_labels.txt");

        double[][] testImages = FileHandling.loadChunkOfImages(2000, "Java-digit-recognition/test.csv");

        //mainActions.learnImages(trainImages, trainLabels);
        //mainActions.learnImages(trainImages, trainLabels);
        //mainActions.learnImages(trainImages, trainLabels);
        //mainActions.learnImages(trainImages, trainLabels);
        //mainActions.learnImages(trainImages, trainLabels);
        //mainActions.saveNeuralNetworkToFile(neuralNetworkFilename);

        //mainActions.showAndCalculateResultRate(trainImages, trainLabels, 0, 20000);
        mainActions.liveTimeLearning(trainImages, trainLabels, 5000);
        mainActions.liveTimeLearning(trainImages, trainLabels, 5000);
        mainActions.liveTimeLearning(trainImages, trainLabels, 5000);
        //mainActions.showNumbersOnScreenAndGuess(testImages,130);


    }
}
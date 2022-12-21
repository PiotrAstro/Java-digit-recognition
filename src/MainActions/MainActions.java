package MainActions;

import AICalculting.NeuralNetwork;
import MatrixCalculations.MatrixDouble;
import PrintingAndDrawing.Drawing;
import PrintingAndDrawing.PrintingNormalText;
import FileHandling.*;
import PrintingAndDrawing.PrintingWorkingMessages;

import java.util.Scanner;

public class MainActions {
    private static int imageSize = 784;
    private static int imageSide = 28;
    private NeuralNetwork neuralNetwork;

    public MainActions(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public MainActions(String filenameToOpenFrom) {
        this.neuralNetwork = (NeuralNetwork) FileHandling.openObjectFromFile(filenameToOpenFrom);
    }

    public void liveTimeLearning(double[][] trainImages, int[] trainLabels, int howMuchAtOnce) {
        int startLearning = 0;
        Scanner scan = new Scanner(System.in);
        String readLine;

        while(startLearning < trainImages.length) {
            //PrintingNormalText.printTextNL(mainActions.generateSaveString());

            learnImages(trainImages, trainLabels, startLearning, howMuchAtOnce);
            showAndCalculateResultRate(trainImages, trainLabels, startLearning + howMuchAtOnce, howMuchAtOnce);
            //mainActions.drawAndPrintResult(trainImages[1]);
            //mainActions.saveNeuralNetworkToFile(neuralNetworkSaveFilename);

            PrintingNormalText.printTextNL("(Enter - kolejny przebieg, \"stop\" - koniec)");
            readLine = scan.nextLine();

            if(readLine.equals("stop")) {
                PrintingNormalText.printTextNL("Zakończono program");
                break;
            }

            startLearning += howMuchAtOnce;
        }
    }

    public void showNumbersOnScreenAndGuess(double[][] testImages) {
        showNumbersOnScreenAndGuess(testImages, 0);
    }
    public void showNumbersOnScreenAndGuess(double[][] testImages, int currentImage) {
        Scanner scan = new Scanner(System.in);
        String readLine = "";

        while(!readLine.equals("stop")) {

            //mainActions.saveNeuralNetworkToFile(neuralNetworkSaveFilename);
            drawAndPrintResult(testImages[currentImage]);

            PrintingNormalText.printTextNL("(Enter - kolejny przebieg, \"stop\" - koniec)");
            readLine = scan.nextLine();
            currentImage ++;
        }
    }

    public void saveNeuralNetworkToFile(String filenameToSaveTo) {
        FileHandling.saveObjectToFile(neuralNetwork, filenameToSaveTo);
    }

    public boolean[] generateTableWithResults(double[][] images, int[] labels) {
        boolean[] results = new boolean[labels.length];

        for(int i = 0; i < results.length; i++) {
            results[i] = getSingleResultPreparedTable(images[i]) == labels[i];
        }

        return results;
    }

    public void showAndCalculateResultRate(double[][] images, int[] labels) {
        showAndCalculateResultRate(images, labels, 0, images.length);
    }

    public void showAndCalculateResultRate(double[][] images, int[] labels, int start, int howMuch) {
        PrintingNormalText.printTextNL("Sprawdzono " + howMuch + " zdjęć, trafialność:\n" + calculateResultRate(images, labels, start, howMuch));
    }

    public double calculateResultRate(double[][] images, int[] labels, int start, int howMuch) {
        int results = 0;

        for(int i = start; i < labels.length && i < howMuch + start; i++) {
            results += (getSingleResultPreparedTable(images[i]) == labels[i] ? 1 : 0);
        }

        return results / (double) howMuch;
    }

    public double calculateResultRate(double[][] images, int[] labels) {
        return calculateResultRate(images, labels, 0, images.length);
    }

    public void learnImages(double[][] images, int[] labels) {
        neuralNetwork.learnImages(images, labels);
        PrintingWorkingMessages.showNLMessage("Sieć nauczyła się " + labels.length + " zdjęć");
    }

    public void learnImages(double[][] images, int[] labels, int whereToStart, int howMuch) {
        neuralNetwork.learnImages(images, labels, whereToStart, howMuch);
        PrintingWorkingMessages.showNLMessage("Sieć zaczęła od zdjęcia " + whereToStart + " i nauczyła się " + howMuch + " zdjęć");
    }

    public String generateSaveString() {
        return neuralNetwork.generateSaveString();
    }

    public void drawAndPrintResult(double[] preparedImage) {
        Drawing.drawImage(preparedImage, imageSide);
        PrintingNormalText.printTextNL("\nWartosc: " + getSingleResultPreparedTable(preparedImage));
    }

    public int getSingleResultPreparedTable(double[] image) {
        return neuralNetwork.calculateResultNumber(image);
    }




    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public static int getImageSize() {
        return imageSize;
    }

    public static void setImageSize(int imageSize) {
        MainActions.imageSize = imageSize;
    }
}

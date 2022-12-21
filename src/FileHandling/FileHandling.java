package FileHandling;

import MainActions.MainActions;
import PrintingAndDrawing.PrintingWorkingMessages;

import java.io.*;
import java.util.Scanner;

public abstract class FileHandling {
    public static final int dividingNumberForImages = 255;

    public static void saveObjectToFile(Object object, String fileName) {
        try (ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oStream.writeObject(object);
            PrintingWorkingMessages.showNLMessage("Zapisano objekt z powodzeniem");
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static Object openObjectFromFile(String fileName) {
        Object object = null;

        try (ObjectInputStream oStream = new ObjectInputStream(new FileInputStream(fileName))) {
            object = oStream.readObject();

            PrintingWorkingMessages.showNLMessage("Wczytano objekt z powodzeniem");
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

        return object;
    }

    public static void saveStringToFile(String safeString, String fileName) {
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(fileName))) {
            bWriter.write(safeString);

            PrintingWorkingMessages.showNLMessage("Zapisano plik z powodzeniem");
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static double[][] loadChunkOfImages(int numberOfImages, String fileName) {
        double[][] result = new double[numberOfImages][MainActions.getImageSize()];
        String readData;
        String [] splittedData;

        try (BufferedReader bReader = new BufferedReader(new FileReader(fileName))) {
            for(int i = 0; i < numberOfImages; i++) {
                readData = bReader.readLine();
                splittedData = readData.split(",");

                for(int k = 0; k < result[i].length; k++) {
                    result[i][k] = ((double) Integer.parseInt(splittedData[k])) / dividingNumberForImages;
                }
            }

            PrintingWorkingMessages.showNLMessage("wczytano zdjęcia z powodzeniem");
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int[] loadChunkOfImageLabels(int numberOfImages, String fileName) {
        int[] result = new int[numberOfImages];

        try (BufferedReader bReader = new BufferedReader(new FileReader(fileName))) {
            Scanner scan = new Scanner(bReader);

            for(int i = 0; i < numberOfImages; i++) {
                result[i] = scan.nextInt();
            }

            PrintingWorkingMessages.showNLMessage("wczytano dane o zdjęciach z powodzeniem");
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}

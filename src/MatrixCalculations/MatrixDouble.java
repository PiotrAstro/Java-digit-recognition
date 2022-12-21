package MatrixCalculations;

import Math.*;

public abstract class MatrixDouble{
    public static double [] copyTable(double[] table) {
        double[] newTable = new double[table.length];

        for(int i = 0; i < table.length; i++) {
            newTable[i] = table[i];
        }

        return newTable;
    }

    public static void setZero(double[] table) {
        for(int i = 0; i < table.length; i++) {
            table[i] = 0;
        }
    }

    public static double [] copyTableParams(double[] table, int whereStart, int howMuch) {
        double[] newTable = new double[table.length];

        for(int i = whereStart; i < howMuch + whereStart; i++) {
            newTable[i] = table[i];
        }

        return newTable;
    }

    public static void add(double[] tableToinsertValues, double[] table) {
        for(int i = 0; i < tableToinsertValues.length; i++) {
            tableToinsertValues[i] += table[i];
        }
    }

    public static void applyActivationFunction(double[] tableToinsertValues, ActivationFunction activationFunction) {
        for(int i = 0; i < tableToinsertValues.length; i++) {
            tableToinsertValues[i] = activationFunction.activationFunction(tableToinsertValues[i]);
        }
    }

    public static String generateSaveString(double[] table) {
        StringBuilder sBuilder = new StringBuilder();

        for(int i = 0; i < table.length; i++) {
            sBuilder.append(table[i]);
            sBuilder.append("\n");
        }

        return sBuilder.toString();
    }
}

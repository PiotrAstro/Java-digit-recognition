package MatrixCalculations;

public abstract class MatrixDouble2d {
    public static void setZero(double[][] table2d) {
        for(int i = 0; i < table2d.length; i++) {
            for(int k = 0; k < table2d[i].length; k++) {
                table2d[i][k] = 0;
            }
        }
    }

    public static double [] multiplyMatrix2dBy1d(double[][] table2d, double[] table1d) {
        double[] newTable = new double[table2d.length];
        double sum;

        for(int i = 0; i < newTable.length; i++) {
            newTable[i] = 0;
            for(int k = 0; k < table2d[i].length; k++) {
                newTable[i] += table2d[i][k] * table1d[k];
            }
        }

        return newTable;
    }

    public void add(double[][] tableToInsertTo, double[][] table) {
        for(int i = 0; i < tableToInsertTo.length; i++) {
            for(int k = 0; k < tableToInsertTo[i].length; k++) {
                tableToInsertTo[i][k] += table[i][k];
            }
        }
    }

    public static String generateSaveString(double[][] table) {
        StringBuilder sBuilder = new StringBuilder();

        for(int i = 0; i < table.length; i++) {
            for(int k = 0; k < table[i].length; k++) {

                sBuilder.append(table[i][k]);
                sBuilder.append(" ");
            }
            sBuilder.append("\n");
        }

        return sBuilder.toString();
    }
}

package PrintingAndDrawing;

public class Drawing {
    private static boolean draw = true;

    public static void drawImage(double [] image, int numberOfColumns) {
        if(draw) {
            int columnCounter = 0;
            StringBuilder sBuilder = new StringBuilder();

            for (int i = 0; i < image.length; i++) {
                sBuilder.append(sign(image[i]));
                columnCounter++;

                if (columnCounter >= numberOfColumns) {
                    sBuilder.append("\n");
                    columnCounter = 0;
                }
            }

            System.out.println(sBuilder.toString());
        }
    }

    public static char sign(double value) {
        char sign = ' ';
        if(value > 0.8) {
            sign = '0';
        }
        else if(value > 0.5) {
            sign = 'o';
        }
        else if(value > 0.1) {
            sign = '.';
        }
        else {
            sign  = ' ';
        }

        return sign;
    }

    public static void drawHR() {
        if(draw) {
            System.out.println("\n\n****************************************************************************************\n\n");
        }
    }

    public static void doDraw() {
        draw = true;
    }

    public static void doNotDraw() {
        draw = false;
    }
}

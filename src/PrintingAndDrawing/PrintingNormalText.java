package PrintingAndDrawing;

public class PrintingNormalText {
    private static boolean normalText = true;

    public static void printTextNL(String message) {
        if(normalText) {
            System.out.println(message);
        }
    }


    public static void doPrintNormalMessages() {
        normalText = true;
    }

    public static void doNotPrintNormalMessages() {
        normalText = false;
    }
}

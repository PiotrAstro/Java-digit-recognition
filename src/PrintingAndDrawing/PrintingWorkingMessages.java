package PrintingAndDrawing;

public abstract class PrintingWorkingMessages {
    private static boolean showWorkingMessages = true;


    public static void showNLMessage(String message) {
        if(showWorkingMessages) {
            System.out.println(message);
        }
    }


    public static void doShowWorkingMessages() {
        showWorkingMessages = true;
    }

    public static void doNotShowWorkingMessages() {
        showWorkingMessages = false;
    }
}

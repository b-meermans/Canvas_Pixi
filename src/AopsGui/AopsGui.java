package AopsGui;

public class AopsGui {
    private static double mouseX;
    private static double mouseY;

    public static void updateMouse(double x, double y) {
        AopsGui.mouseX = x;
        AopsGui.mouseY = y;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }
}

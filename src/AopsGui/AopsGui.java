package AopsGui;

import java.util.*;

public class AopsGui {
    private static double mouseX;
    private static double mouseY;

    private static boolean isMousePressed;

    private static final Set<String> keysPressed = new HashSet<>();

    public static void updateMouse(double x, double y, boolean isMousePressed) {
        AopsGui.mouseX = x;
        AopsGui.mouseY = y;
        AopsGui.isMousePressed = isMousePressed;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static boolean isMousePressed() {
        return isMousePressed;
    }

    public static void updateKeys(String[] keys) {
        keysPressed.clear();
        Collections.addAll(keysPressed, keys);
    }

    public static boolean isKeyPressed(String key) {
        return keysPressed.contains(key.toUpperCase());
    }
}

package AopsGui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AopsGui {
    private static double mouseX;
    private static double mouseY;

    private static final Set<String> keysPressed = new HashSet<>();


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

    public static void updateKeys(String[] keys) {
        keysPressed.clear();
        keysPressed.addAll(Arrays.asList(keys));
    }

    public static boolean isKeyPressed(String key) {
        if (key.equals(" ")) {
            key = "space";
        }

        return keysPressed.contains(key.toLowerCase());
    }
}

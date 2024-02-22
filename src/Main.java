import AopsTheater.*;
import StudentCode.Balloon;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AopsTheater aopsTheater = AopsTheater.build();


        TheaterArt.drawEllipse(0, 0, 100, 200.5);
        TheaterArt.drawFilledEllipse(0, 0, 100, 200.5);
        TheaterArt.drawLine(30, 40, -20, 50);
        TheaterArt.setBorderColor(Color.GREEN);
        TheaterArt.drawFilledRectangle(1, 1, 3, 3);
        TheaterArt.drawRoundedRectangle(1, 1, 3, 3, 1);

        double[] xs = {0, 5, 19, 100};
        double[] ys = {40, 20, 10};
        TheaterArt.drawFilledPolygon(xs, ys);


        System.out.println(aopsTheater.getState());
//        System.out.println(aopsTheater.getMethodsJSON("A"));

//
//        String json = "{\n" +
//                "  \"numberOfUpdates\": 1,\n" +
//                "  \"playerEvents\": [\n" +
//                "    {\n" +
//                "      \"playerId\": 1,\n" +
//                "      \"mouseX\": 440.5,\n" +
//                "      \"mouseY\": 590.265625,\n" +
//                "      \"leftMouseClick\": false,\n" +
//                "      \"rightMouseClick\": false,\n" +
//                "      \"pressedKeys\": [\n" +
//                "        \"3\",\n" +
//                "        \"4\",\n" +
//                "        \"5\"\n" +
//                "      ]\n" +
//                "    }\n," +
//                "    {\n" +
//                "      \"playerId\": 1,\n" +
//                "      \"mouseX\": 2.5,\n" +
//                "      \"mouseY\": 590.265625,\n" +
//                "      \"leftMouseClick\": false,\n" +
//                "      \"rightMouseClick\": false,\n" +
//                "      \"pressedKeys\": [\n" +
//                "        \"3\",\n" +
//                "        \"4\",\n" +
//                "        \"5\"\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//
//        String j = "{\n" +
//                "  \"UUID\": \"E28394818123128\",\n" +
//                "  \"method\": \"move\",\n" +
//                "  \"returnType\": \"void\",\n" +
//                "  \"parameters\": [\n" +
//                "    {\n" +
//                "      \"type\": \"double\",\n" +
//                "      \"value\": 45.2\n" +
//                "    }" +
//                "  ]\n" +
//                "}";
//
//        aopsTheater.invokeMethod("{\n" +
//                "  \"UUID\": \"A\",\n" +
//                "  \"method\": \"setLocation\",\n" +
//                "  \"parameters\": [\n" +
//                "    {\n" +
//                "      \"type\": \"double\",\n" +
//                "      \"value\": 200\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"type\": \"double\",\n" +
//                "      \"value\": 100\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}");

        String result = aopsTheater.invokeMethod("{\n" +
                "  \"UUID\": \"A\",\n" +
                "  \"method\": \"getX\",\n" +
                "  \"parameters\": []\n" +
                "}");

        System.out.println(result);
    }
}

import AopsTheater.*;
import java.awt.Color;
import java.util.Arrays;

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
//
//        System.out.println(aopsTheater.getState());
//        System.out.println(aopsTheater.getMethodsJSON(null));

        String json = "{\n" +
                "  \"numberOfUpdates\": 1,\n" +
                "  \"playerEvents\": [\n" +
                "    {\n" +
                "      \"playerId\": 1,\n" +
                "      \"mouseX\": 316,\n" +
                "      \"mouseY\": 587.265625,\n" +
                "      \"leftMouseClick\": false,\n" +
                "      \"rightMouseClick\": false,\n" +
                "      \"pressedKeys\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";

        Events.parseJSON(json);
    }
}

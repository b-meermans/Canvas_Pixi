package AopsTheater;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TheaterArt {
    // TODO Remove the JSON calculations from here and put the in JsonConversion

    private static List<String> shapes = new ArrayList<>();
    private static Color fillColor = Color.WHITE;
    private static Color borderColor = Color.BLACK;
    private static Color lineColor = Color.BLACK;
    private static double thickness = 1;
    private static double alpha = 1;

    public static void clear() {
        shapes.clear();
    }

    public static void drawEllipse(double x, double y, double width, double height) {
        drawEllipse(x, y, width, height, false);
    }

    private static void drawEllipse(double x, double y, double width, double height, boolean isFilled) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        JsonConversion.appendField(jsonBuilder, "type", "ellipse");
        JsonConversion.appendField(jsonBuilder, "x", x);
        JsonConversion.appendField(jsonBuilder, "y", y);
        JsonConversion.appendField(jsonBuilder, "width", width);
        JsonConversion.appendField(jsonBuilder, "height", height);
        JsonConversion.appendField(jsonBuilder, "borderColor", borderColor);
        JsonConversion.appendField(jsonBuilder, "thickness", thickness);
        JsonConversion.appendField(jsonBuilder, "alpha", alpha);

        if (isFilled) {
            JsonConversion.appendField(jsonBuilder, "fillColor", fillColor);
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        shapes.add(jsonBuilder.toString());
    }

    public static void drawFilledEllipse(double x, double y, double width, double height) {
        drawEllipse(x, y, width, height, true);
    }

    public static void drawFilledPolygon(double[] xs, double[] ys) {
        drawPolygon(xs, ys, true);
    }

    public static void drawFilledRectangle(double x, double y, double width, double height) {
        drawRect(x, y, width, height, 0, true);
    }

    public static void drawFilledRoundedRectangle(double x, double y, double width, double height, double radius) {
        drawRect(x, y, width, height, radius, true);
    }

    public static void drawLine(double x1, double y1, double x2, double y2) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        JsonConversion.appendField(jsonBuilder, "type", "line");
        JsonConversion.appendField(jsonBuilder, "x1", x1);
        JsonConversion.appendField(jsonBuilder, "y1", y1);
        JsonConversion.appendField(jsonBuilder, "x2", x2);
        JsonConversion.appendField(jsonBuilder, "y2", y2);
        JsonConversion.appendField(jsonBuilder, "color", lineColor);
        JsonConversion.appendField(jsonBuilder, "width", thickness);
        JsonConversion.appendField(jsonBuilder, "alpha", alpha);
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");;

        shapes.add(jsonBuilder.toString());
    }

    public static void drawPolygon(double[] xs, double[] ys) {
        drawPolygon(xs, ys, false);
    }

    private static void drawPolygon(double[] xs, double[] ys, boolean isFilled) {
        int length = Math.min(xs.length, ys.length) * 2;
        double[] mergedCoordinates = new double[length];

        for (int i = 0, j = 0, k = 0; k < length; k++) {
            mergedCoordinates[k] = (k % 2 == 0) ? xs[i++] : ys[j++];
        }



        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        JsonConversion.appendField(jsonBuilder, "type", "polygon");
        JsonConversion.appendField(jsonBuilder, "coordinates", mergedCoordinates);
        JsonConversion.appendField(jsonBuilder, "borderColor", borderColor);
        JsonConversion.appendField(jsonBuilder, "thickness", thickness);
        JsonConversion.appendField(jsonBuilder, "alpha", alpha);

        if (isFilled) {
            JsonConversion.appendField(jsonBuilder, "fillColor", fillColor);
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        shapes.add(jsonBuilder.toString());
    }

    private static void drawRect(double x, double y, double width, double height, double radius, boolean isFilled) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        if (radius > 0) {
            JsonConversion.appendField(jsonBuilder, "type", "roundedrectangle");
            JsonConversion.appendField(jsonBuilder, "radius", radius);
        } else {
            JsonConversion.appendField(jsonBuilder, "type", "rectangle");
        }

        JsonConversion.appendField(jsonBuilder, "x", x);
        JsonConversion.appendField(jsonBuilder, "y", y);
        JsonConversion.appendField(jsonBuilder, "width", width);
        JsonConversion.appendField(jsonBuilder, "height", height);
        JsonConversion.appendField(jsonBuilder, "borderColor", borderColor);
        JsonConversion.appendField(jsonBuilder, "thickness", thickness);
        JsonConversion.appendField(jsonBuilder, "alpha", alpha);

        if (isFilled) {
            JsonConversion.appendField(jsonBuilder, "fillColor", fillColor);
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        shapes.add(jsonBuilder.toString());
    }

    public static void drawRectangle(double x, double y, double width, double height) {
        drawRect(x, y, width, height, 0, false);
    }

    public static void drawRoundedRectangle(double x, double y, double width, double height, double radius) {
        drawRect(x, y, width, height, radius, false);
    }

    public static Color getBorderColor() {
        return borderColor;
    }

    public static void setBorderColor(Color borderColor) {
        TheaterArt.borderColor = borderColor;
    }

    public static Color getFillColor() {
        return fillColor;
    }

    public static void setFillColor(Color fillColor) {
        TheaterArt.fillColor = fillColor;
    }

    public static Color getLineColor() {
        return lineColor;
    }

    public static void setLineColor(Color lineColor) {
        TheaterArt.lineColor = lineColor;
    }

    static List<String> getShapes() {
        return shapes;
    }

    public static double getThickness() {
        return thickness;
    }

    public static void setThickness(double thickness) {
        TheaterArt.thickness = Math.max(0, thickness);
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = Math.max(0.0, Math.min(1.0, alpha));
    }


}

package AopsTheater;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.awt.*;

import static AopsTheater.JsonConversion.colorToInt;

public class TheaterArt {
    private static JsonArray shapes = new JsonArray();
    private static Color fillColor = Color.WHITE;
    private static Color borderColor = Color.BLACK;
    private static Color lineColor = Color.BLACK;
    private static double thickness = 1;
    private static double alpha = 1;

    public static void clear() {
        shapes = new JsonArray();
    }

    private static void addShape(JsonObject shape) {
        shapes.add(shape);
    }

    private static JsonObject createBaseShape(String type) {
        JsonObject shape = new JsonObject();
        shape.addProperty("type", type);
        shape.addProperty("borderColor", colorToInt(borderColor));
        shape.addProperty("thickness", thickness);
        shape.addProperty("alpha", alpha);
        return shape;
    }

    public static void drawEllipse(double x, double y, double width, double height) {
        drawEllipse(x, y, width, height, false);
    }

    private static void drawEllipse(double x, double y, double width, double height, boolean isFilled) {
        JsonObject ellipse = createBaseShape("ellipse");
        ellipse.addProperty("x", x);
        ellipse.addProperty("y", y);
        ellipse.addProperty("width", width);
        ellipse.addProperty("height", height);

        if (isFilled) {
            ellipse.addProperty("fillColor", colorToInt(fillColor));
        }

        addShape(ellipse);
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
        JsonObject line = new JsonObject();
        line.addProperty("type", "line");
        line.addProperty("x1", x1);
        line.addProperty("y1", y1);
        line.addProperty("x2", x2);
        line.addProperty("y2", y2);
        line.addProperty("color", colorToInt(lineColor));
        line.addProperty("width", thickness);
        line.addProperty("alpha", alpha);

        addShape(line);
    }

    public static void drawPolygon(double[] xs, double[] ys) {
        drawPolygon(xs, ys, false);
    }

    private static void drawPolygon(double[] xs, double[] ys, boolean isFilled) {
        int length = Math.min(xs.length, ys.length);
        JsonArray coordinates = new JsonArray();

        for (int i = 0; i < length; i++) {
            JsonArray point = new JsonArray();
            point.add(xs[i]);
            point.add(ys[i]);
            coordinates.add(point);
        }

        JsonObject polygon = createBaseShape("polygon");
        polygon.add("coordinates", coordinates);

        if (isFilled) {
            polygon.addProperty("fillColor", colorToInt(fillColor));
        }

        addShape(polygon);
    }

    private static void drawRect(double x, double y, double width, double height, double radius, boolean isFilled) {
        JsonObject rectangle;

        if (radius > 0) {
            rectangle = createBaseShape("roundedrectangle");
            rectangle.addProperty("radius", radius);
        } else {
            rectangle = createBaseShape("rectangle");
        }

        rectangle.addProperty("x", x);
        rectangle.addProperty("y", y);
        rectangle.addProperty("width", width);
        rectangle.addProperty("height", height);

        if (isFilled) {
            rectangle.addProperty("fillColor", colorToInt(fillColor));
        }

        addShape(rectangle);
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

    public static JsonArray getShapes() {
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

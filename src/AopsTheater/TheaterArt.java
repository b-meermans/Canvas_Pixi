package AopsTheater;

import JsonSimple.JSONArray;
import JsonSimple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TheaterArt {
    private static final JSONArray shapes = new JSONArray();
    private static Color fillColor = Color.WHITE;
    private static Color borderColor = Color.BLACK;
    private static Color lineColor = Color.BLACK;
    private static double thickness = 1;
    private static double alpha = 1;

    public static void clear() {
        shapes.clear();
    }

    private static void addShape(JSONObject shape) {
        shapes.add(shape);
    }

    private static JSONObject createBaseShape(String type) {
        JSONObject shape = new JSONObject();
        shape.put("type", type);
        shape.put("borderColor", JsonConversion.colorToInt(borderColor));
        shape.put("thickness", thickness);
        shape.put("alpha", alpha);
        return shape;
    }

    public static void drawEllipse(double x, double y, double width, double height) {
        drawEllipse(x, y, width, height, false);
    }

    private static void drawEllipse(double x, double y, double width, double height, boolean isFilled) {
        JSONObject ellipse = createBaseShape("ellipse");
        ellipse.put("x", x);
        ellipse.put("y", y);
        ellipse.put("width", width);
        ellipse.put("height", height);

        if (isFilled) {
            ellipse.put("fillColor", JsonConversion.colorToInt(fillColor));
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
        JSONObject line = new JSONObject();
        line.put("type", "line");
        line.put("x1", x1);
        line.put("y1", y1);
        line.put("x2", x2);
        line.put("y2", y2);
        line.put("color", JsonConversion.colorToInt(lineColor));
        line.put("width", thickness);
        line.put("alpha", alpha);

        addShape(line);
    }

    public static void drawPolygon(double[] xs, double[] ys) {
        drawPolygon(xs, ys, false);
    }

    private static void drawPolygon(double[] xs, double[] ys, boolean isFilled) {
        int length = Math.min(xs.length, ys.length);
        JSONArray coordinates = new JSONArray();

        for (int i = 0; i < length; i++) {
            JSONArray point = new JSONArray();
            point.add(xs[i]);
            point.add(ys[i]);
            coordinates.add(point);
        }

        JSONObject polygon = createBaseShape("polygon");
        polygon.put("coordinates", coordinates);

        if (isFilled) {
            polygon.put("fillColor", JsonConversion.colorToInt(fillColor));
        }

        addShape(polygon);
    }

    private static void drawRect(double x, double y, double width, double height, double radius, boolean isFilled) {
        JSONObject rectangle;

        if (radius > 0) {
            rectangle = createBaseShape("roundedrectangle");
            rectangle.put("radius", radius);
        } else {
            rectangle = createBaseShape("rectangle");
        }

        rectangle.put("x", x);
        rectangle.put("y", y);
        rectangle.put("width", width);
        rectangle.put("height", height);

        if (isFilled) {
            rectangle.put("fillColor", JsonConversion.colorToInt(fillColor));
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

    static JSONArray getShapes() {
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

package AopsTheater;

import java.awt.*;
import java.util.UUID;

public class Text {
    // TODO Revisit this - no thought put into it.

    UUID uuid;

    private String text;
    private double x;
    private double y;
    private int z;
    private double rotation;

    private boolean italic;
    private boolean bold;
    private boolean underlined;
    private boolean wordWrap;
    private int wordWrapWidth = Integer.MAX_VALUE;

    private Color color = Color.BLACK;
    private Font font = Font.COURIER_NEW;
    private Alignment alignment = Alignment.LEFT;

    private Stage stage;

    enum Font {
        ARIAL,
        HELVETICA,
        TIMES_NEW_ROMAN,
        COURIER_NEW,
        VERDANA,
        GEORGIA;

        public String toString() {
            return this.name().replaceAll("_", " ");
        }
    }

    enum Alignment {
        LEFT,
        CENTER,
        RIGHT;
    }

    public Text(String text) {
        this.text = text;
        uuid = UUID.randomUUID();
    }

    String getID() {
        return uuid.toString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getX() {
        return x;
    }

    public void setLocation(double x, double y) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public boolean isWordWrap() {
        return wordWrap;
    }

    public void setWordWrap(boolean wordWrap) {
        this.wordWrap = wordWrap;
    }

    public int getWordWrapWidth() {
        return wordWrapWidth;
    }

    public void setWordWrapWidth(int wordWrapWidth) {
        this.wordWrapWidth = wordWrapWidth;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

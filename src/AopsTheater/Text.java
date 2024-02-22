package AopsTheater;

import java.awt.*;

public class Text extends AopsTheaterComponent {
    private transient Stage stage;

    private double x;
    private double y;
    private int z;  // TODO Think through how Z will work
    private double rotation;
    private double alpha = 1;
    private boolean isVisible = true;

    private String content;

    private boolean italic;
    private boolean bold;
    private boolean underlined;
    private boolean wordWrap;
    private int wordWrapWidth = Integer.MAX_VALUE;

    private Color fontColor = Color.BLACK;
    private Font font = Font.COURIER_NEW;
    private int size = 16;
    private Alignment alignment = Alignment.LEFT;

    public Text(String content) {
        this.content = content;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = Math.max(0.0, Math.min(1.0, alpha));
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public double getRotation() {
        return this.rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation % 360;
        this.rotation += (this.rotation < 0) ? 360 : 0;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Stage getStage() {
        return stage;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getContent() {
        return content;
    }

    public void setText(String content) {
        this.content = content;
    }

    public int getWordWrapWidth() {
        return wordWrapWidth;
    }

    public void setWordWrapWidth(int wordWrapWidth) {
        this.wordWrapWidth = wordWrapWidth;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getZ() {
        return z;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
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

    public void move(double distance) {
        x += Math.cos(rotation) * distance;
        y += Math.sin(rotation) * distance;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String toString() {
        return "AopsGui.Actor{x=" + this.x + ", y=" + this.y + ", rotation=" + this.rotation + "}";
    }

    public void turn(double degrees) {
        rotation += degrees;
    }

    public void turnTowards(double x, double y) {
        rotation = Math.atan2(y - this.y, x - this.x);
    }

    // TODO Look into Fonts, this is a guess
    enum Font {
        ARIAL,
        HELVETICA,
        TIMES_NEW_ROMAN,
        COURIER_NEW,
        VERDANA,
        GEORGIA;
    }

    enum Alignment {
        LEFT,
        CENTER,
        RIGHT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}

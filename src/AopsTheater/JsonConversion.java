package AopsTheater;

import java.awt.*;
import java.util.List;

public class JsonConversion {

    public static String getStateJson(Stage stage) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{")
                .append("\"stage\":").append(getStageJson(stage)).append(",")
                .append("\"actors\":").append(getActorJson(stage.getActors())).append(",")
                .append("\"texts\":").append(getTextJson(stage.getTexts())).append(",")
                .append("\"sounds\":").append(getSoundJson(stage.getSounds()))
                .append("}");
        return jsonBuilder.toString();
    }

    public static String getStageJson(Stage stage) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{")
                .append("\"width\":").append(stage.getWidth()).append(",")
                .append("\"height\":").append(stage.getHeight()).append(",")
                .append("\"image\":\"").append(stage.getImage()).append("\"")
                .append("}");
        return jsonBuilder.toString();
    }

    public static String getActorJson(List<Actor> actors) {
        StringBuilder actorBuilder = new StringBuilder("[");
        for (Actor actor : actors) {
            actorBuilder.append(JsonConversion.convertToJson(actor)).append(",");
        }
        actorBuilder.deleteCharAt(actorBuilder.length() - 1);
        actorBuilder.append("]");

        return actorBuilder.toString();
    }

    public static String getTextJson(List<Text> texts) {
        StringBuilder textBuilder = new StringBuilder("[");
        for (Text text : texts) {
            textBuilder.append(JsonConversion.convertToJson(text)).append(",");
        }
        textBuilder.deleteCharAt(textBuilder.length() - 1);
        textBuilder.append("]");

        return textBuilder.toString();
    }

    public static String getSoundJson(List<Sound> sounds) {
        StringBuilder textBuilder = new StringBuilder("[");
        for (Sound sound : sounds) {
            textBuilder.append(JsonConversion.convertToJson(sound)).append(",");
        }
        textBuilder.deleteCharAt(textBuilder.length() - 1);
        textBuilder.append("]");

        return textBuilder.toString();
    }

    private static String convertToJson(Text text) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        appendField(jsonBuilder, "uuid", "\"" + text.getID() + "\"");
        appendField(jsonBuilder, "x", Double.toString(text.getX()));
        appendField(jsonBuilder, "y", Double.toString(text.getY()));
        appendField(jsonBuilder, "z", Double.toString(text.getZ()));
        appendField(jsonBuilder, "rotation", Double.toString(text.getRotation()));
        appendField(jsonBuilder, "text", "\"" + text.getText() + "\"");
        appendField(jsonBuilder, "italic", Boolean.toString(text.isItalic()));
        appendField(jsonBuilder, "bold", Boolean.toString(text.isBold()));
        appendField(jsonBuilder, "underlined", Boolean.toString(text.isUnderlined()));
        appendField(jsonBuilder, "wordWrap", Boolean.toString(text.isWordWrap()));
        appendField(jsonBuilder, "wordWrapWidth", Integer.toString(text.getWordWrapWidth()));
        appendField(jsonBuilder, "color", Integer.toString(colorToHex(text.getColor())));
        appendField(jsonBuilder, "font", "\"" + text.getFont().toString() + "\"");
        appendField(jsonBuilder, "alignment", "\"" + text.getAlignment().name() + "\"");
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    private static String convertToJson(Actor actor) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        appendField(jsonBuilder, "uuid", "\"" + actor.getID() + "\"");
        appendField(jsonBuilder, "x", Double.toString(actor.getX()));
        appendField(jsonBuilder, "y", Double.toString(actor.getY()));
        appendField(jsonBuilder, "z", Double.toString(actor.getZ()));
        appendField(jsonBuilder, "rotation", Double.toString(actor.getRotation()));
        appendField(jsonBuilder, "image", "\"" + actor.getImage() + "\"");
        appendField(jsonBuilder, "width", Double.toString(actor.getWidth()));
        appendField(jsonBuilder, "height", Double.toString(actor.getHeight()));
        appendField(jsonBuilder, "tint", Integer.toString(colorToHex(actor.getTint())));
        appendField(jsonBuilder, "alpha", Double.toString(actor.getAlpha()));
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    private static String convertToJson(Sound sound) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        appendField(jsonBuilder, "id", "\"" + sound.getId() + "\"");
        appendField(jsonBuilder, "fileName", "\"" + sound.getFileName() + "\"");
        appendField(jsonBuilder, "volume", Double.toString(sound.getVolume()));
        appendField(jsonBuilder, "status", "\"" + sound.getStatus() + "\"");
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    private static void appendField(StringBuilder builder, String fieldName, String fieldValue) {
        builder.append("\"").append(fieldName).append("\":").append(fieldValue).append(",");
    }

    private static int colorToHex(Color color) {
        return color.getRGB() & 0xFFFFFF;
    }
}

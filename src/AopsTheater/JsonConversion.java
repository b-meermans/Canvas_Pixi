package AopsTheater;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class JsonConversion {

    static String getStateJson(Stage stage) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{")
                .append("\"stage\":").append(convertToJson(stage)).append(",")
                .append("\"actors\":").append(getActorJson(stage.getActors())).append(",")
                .append("\"texts\":").append(getTextJson(stage.getTexts())).append(",")
                .append("\"sounds\":").append(getSoundJson(stage.getSounds())).append(",")
                .append("\"shapes\":").append(TheaterArt.getShapes())
                .append("}");
        return jsonBuilder.toString();
    }

    private static String getActorJson(List<Actor> actors) {
        StringBuilder actorBuilder = new StringBuilder("[");
        for (Actor actor : actors) {
            actorBuilder.append(JsonConversion.convertToJson(actor)).append(",");
        }
        actorBuilder.deleteCharAt(actorBuilder.length() - 1);
        actorBuilder.append("]");

        return actorBuilder.toString();
    }

    private static String getTextJson(List<Text> texts) {
        StringBuilder textBuilder = new StringBuilder("[");
        for (Text text : texts) {
            textBuilder.append(JsonConversion.convertToJson(text)).append(",");
        }
        textBuilder.deleteCharAt(textBuilder.length() - 1);
        textBuilder.append("]");

        return textBuilder.toString();
    }

    private static String getSoundJson(List<Sound> sounds) {
        StringBuilder textBuilder = new StringBuilder("[");
        for (Sound sound : sounds) {
            textBuilder.append(JsonConversion.convertToJson(sound)).append(",");
        }
        textBuilder.deleteCharAt(textBuilder.length() - 1);
        textBuilder.append("]");

        return textBuilder.toString();
    }

    private static String convertToJson(Stage stage) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        appendField(jsonBuilder, "width", stage.getWidth());
        appendField(jsonBuilder, "height", stage.getHeight());
        appendField(jsonBuilder, "image", stage.getImage());

        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private static String convertToJson(Text text) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        appendField(jsonBuilder, "uuid", text.getUUID());
        appendField(jsonBuilder, "x", text.getX());
        appendField(jsonBuilder, "y", text.getY());
        appendField(jsonBuilder, "z", text.getZ());
        appendField(jsonBuilder, "rotation", text.getRotation());
        appendField(jsonBuilder, "text", text.getText());
        appendField(jsonBuilder, "italic", text.isItalic());
        appendField(jsonBuilder, "bold", text.isBold());
        appendField(jsonBuilder, "underlined", text.isUnderlined());
        appendField(jsonBuilder, "wordWrap", text.isWordWrap());
        appendField(jsonBuilder, "wordWrapWidth", text.getWordWrapWidth());
        appendField(jsonBuilder, "color", text.getFontColor());
        appendField(jsonBuilder, "font", text.getFont());
        appendField(jsonBuilder, "alignment", text.getAlignment());

        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    private static String convertToJson(Actor actor) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");


        appendField(jsonBuilder, "uuid", actor.getUUID());
        appendField(jsonBuilder, "x", actor.getX());
        appendField(jsonBuilder, "y", actor.getY());
        appendField(jsonBuilder, "z", actor.getZ());
        appendField(jsonBuilder, "rotation", actor.getRotation());
        appendField(jsonBuilder, "image", actor.getImage());
        appendField(jsonBuilder, "width", actor.getWidth());
        appendField(jsonBuilder, "height", actor.getHeight());
        appendField(jsonBuilder, "tint", actor.getTint());
        appendField(jsonBuilder, "alpha", actor.getAlpha());

        if (actor instanceof AnimatedActor) {
            AnimatedActor animatedActor = (AnimatedActor) actor;
            appendField(jsonBuilder, "animated", animatedActor.isPlaying());
        }

        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    private static String convertToJson(Sound sound) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        appendField(jsonBuilder, "uuid", sound.getUUID());
        appendField(jsonBuilder, "fileName", sound.getFileName());
        appendField(jsonBuilder, "volume", sound.getVolume());
        appendField(jsonBuilder, "status", sound.getStatus());

        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    static void appendField(StringBuilder builder, String fieldName, String fieldValue) {
        builder.append("\"").append(fieldName).append("\":\"").append(fieldValue).append("\",");
    }

    static void appendField(StringBuilder builder, String fieldName, Color fieldValue) {
        appendField(builder, fieldName, colorToInt(fieldValue));
    }

    static void appendField(StringBuilder builder, String fieldName, Enum<?> fieldValue) {
        builder.append("\"").append(fieldName).append("\":\"").append(fieldValue.name().replace("_", " ")).append("\",");
    }

    static void appendField(StringBuilder builder, String fieldName, Object fieldValue) {
        builder.append("\"").append(fieldName).append("\":").append(fieldValue).append(",");
    }

    static void appendField(StringBuilder builder, String fieldName, double[] values) {
        builder.append("\"").append(fieldName).append("\":").append(Arrays.toString(values)).append(",");
    }


    static int colorToInt(Color color) {
        return color.getRGB() & 0xFFFFFF;
    }

}

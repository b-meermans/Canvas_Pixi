package AopsTheater;

import JsonSimple.JSONArray;
import JsonSimple.JSONObject;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class JsonConversion {

    static JSONObject getStateJson(Stage stage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stage", convertToJson(stage));
        jsonObject.put("actors", getActorJson(stage.getActors()));
        jsonObject.put("texts", getTextJson(stage.getTexts()));
        jsonObject.put("sounds", getSoundJson(stage.getSounds()));
        jsonObject.put("shapes", TheaterArt.getShapes()); // Assuming getShapes() returns a JSON compatible object
        return jsonObject;
    }

    private static JSONArray getActorJson(List<Actor> actors) {
        JSONArray jsonArray = new JSONArray();
        for (Actor actor : actors) {
            if (actor.isVisible()) {
                jsonArray.add(convertToJson(actor));
            }
        }
        return jsonArray;
    }

    private static JSONArray getTextJson(List<Text> texts) {
        JSONArray jsonArray = new JSONArray();
        for (Text text : texts) {
            if (text.isVisible()) {
                jsonArray.add(convertToJson(text));
            }
        }
        return jsonArray;
    }

    private static JSONArray getSoundJson(List<Sound> sounds) {
        JSONArray jsonArray = new JSONArray();
        for (Sound sound : sounds) {
            jsonArray.add(convertToJson(sound));
        }
        return jsonArray;
    }

    private static JSONObject convertToJson(Stage stage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("width", stage.getWidth());
        jsonObject.put("height", stage.getHeight());
        jsonObject.put("image", stage.getImage());
        return jsonObject;
    }

    private static JSONObject convertToJson(Text text) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("uuid", text.getUUID());
        jsonObject.put("x", text.getX());
        jsonObject.put("y", text.getY());
        jsonObject.put("z", text.getZ());
        jsonObject.put("rotation", text.getRotation());
        jsonObject.put("text", text.getContent());
        jsonObject.put("italic", text.isItalic());
        jsonObject.put("bold", text.isBold());
        jsonObject.put("underlined", text.isUnderlined());
        jsonObject.put("wordWrap", text.isWordWrap());
        jsonObject.put("wordWrapWidth", text.getWordWrapWidth());
        jsonObject.put("color", colorToInt(text.getFontColor()));
        jsonObject.put("font", text.getFont().toString());
        jsonObject.put("alignment", text.getAlignment().toString());

        return jsonObject;
    }


    private static JSONObject convertToJson(Actor actor) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("uuid", actor.getUUID());
        jsonObject.put("x", actor.getX());
        jsonObject.put("y", actor.getY());
        jsonObject.put("z", actor.getZ());
        jsonObject.put("rotation", actor.getRotation());
        jsonObject.put("image", actor.getImage());
        jsonObject.put("width", actor.getWidth());
        jsonObject.put("height", actor.getHeight());
        jsonObject.put("tint", colorToInt(actor.getTint()));
        jsonObject.put("alpha", actor.getAlpha());

        if (actor instanceof AnimatedActor) {
            AnimatedActor animatedActor = (AnimatedActor) actor;
            jsonObject.put("animated", animatedActor.isPlaying());
        }

        return jsonObject;
    }

    private static JSONObject convertToJson(Sound sound) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("uuid", sound.getUUID());
        jsonObject.put("fileName", sound.getFileName());
        jsonObject.put("volume", sound.getVolume());
        jsonObject.put("status", sound.getStatus().toString());

        return jsonObject;
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

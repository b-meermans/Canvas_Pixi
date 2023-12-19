package AopsGui;

import java.util.List;

public class JsonConversion {


    public static String getActorJason(List<Actor> actors) {
        StringBuilder actorBuilder = new StringBuilder("[");
        for (Actor actor : actors) {
            actorBuilder.append(JsonConversion.convertToJson(actor)).append(",");
        }
        actorBuilder.deleteCharAt(actorBuilder.length() - 1);
        actorBuilder.append("]");

        return actorBuilder.toString();
    }

    private static String convertToJson(Actor actor) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        appendField(jsonBuilder, "uuid", "\"" + actor.getID() + "\"");
        appendField(jsonBuilder, "x", Double.toString(actor.getX()));
        appendField(jsonBuilder, "y", Double.toString(actor.getY()));
        appendField(jsonBuilder, "rotation", Double.toString(actor.getRotation()));
        appendField(jsonBuilder, "image", "\"" + actor.getImage() + "\"");
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    private static void appendField(StringBuilder builder, String fieldName, String fieldValue) {
        builder.append("\"").append(fieldName).append("\":").append(fieldValue).append(",");
    }

}

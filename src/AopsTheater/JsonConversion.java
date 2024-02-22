package AopsTheater;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.*;
import java.util.List;

public class JsonConversion {
    private static final Gson gson = new Gson();

    static JsonObject getStateJson(Stage stage) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("stage", gson.toJsonTree(stage));
        jsonObject.add("actors", getActorJson(stage.getActors()));
        jsonObject.add("texts", getTextJson(stage.getTexts()));
        jsonObject.add("sounds", getSoundJson(stage.getSounds()));
        jsonObject.add("shapes", gson.toJsonTree(TheaterArt.getShapes())); // Assuming getShapes() returns a JSON compatible object
        return jsonObject;
    }

    private static JsonArray getActorJson(List<Actor> actors) {
        JsonArray jsonArray = new JsonArray();
        for (Actor actor : actors) {
            if (actor.isVisible()) {
                jsonArray.add(gson.toJsonTree(actor));
            }
        }
        return jsonArray;
    }

    private static JsonArray getTextJson(List<Text> texts) {
        JsonArray jsonArray = new JsonArray();
        for (Text text : texts) {
            if (text.isVisible()) {
                jsonArray.add(gson.toJsonTree(text));
            }
        }
        return jsonArray;
    }

    private static JsonArray getSoundJson(List<Sound> sounds) {
        JsonArray jsonArray = new JsonArray();
        for (Sound sound : sounds) {
            jsonArray.add(gson.toJsonTree(sound));
        }
        return jsonArray;
    }

    static int colorToInt(Color color) {
        return color.getRGB() & 0xFFFFFF;
    }
}

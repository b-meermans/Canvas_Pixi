package AopsTheater;

import JsonSimple.JSONArray;
import JsonSimple.JSONObject;
import JsonSimple.parser.JSONParser;
import JsonSimple.parser.ParseException;

import java.util.*;

public class Events {

    private static int numUpdates;
    private static Integer defaultPlayer = 1;
    private static Map<Integer, PlayerEvent> playerIDToPlayerEvent;

    static void reset() {
        numUpdates = 1;
        if (playerIDToPlayerEvent == null) {
            playerIDToPlayerEvent = new HashMap<>();
        } else {
            playerIDToPlayerEvent.clear();
        }
    }

    static void fromJSON(String json) {
        playerIDToPlayerEvent.clear();
        parseJSON(json);
    }

    static int getNumUpdates() {
        return numUpdates;
    }

    public static double getMouseX() {
        return playerIDToPlayerEvent.get(defaultPlayer).getMouseX();
    }

    public static double getMouseY() {
        return playerIDToPlayerEvent.get(defaultPlayer).getMouseY();
    }

    public static boolean isMousePressed() {
        return playerIDToPlayerEvent.get(defaultPlayer).isLeftMouseClick();
    }

    public static boolean isKeyPressed(String key) {
        return playerIDToPlayerEvent.get(defaultPlayer).isKeyPressed(key.toUpperCase());
    }

    public static boolean isKeyPressed(int playerID, String key) {
        key = key.toUpperCase();
        PlayerEvent playerEvent = playerIDToPlayerEvent.getOrDefault(playerID, null);
        return playerEvent != null && playerEvent.isKeyPressed(key);
    }

    public static void parseJSON(String jsonInput) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject rootObject = (JSONObject) parser.parse(jsonInput);
            numUpdates = ((Long) rootObject.get("numberOfUpdates")).intValue();

            JSONArray playerEventsArray = (JSONArray) rootObject.get("playerEvents");
            for (Object o : playerEventsArray) {
                JSONObject playerEventObj = (JSONObject) o;
                PlayerEvent.Builder playerBuilder = new PlayerEvent.Builder();

                playerBuilder.setID(((Long) playerEventObj.get("playerId")).intValue());
                playerBuilder.mouseX((Double) playerEventObj.get("mouseX"));
                playerBuilder.mouseY((Double) playerEventObj.get("mouseY"));
                playerBuilder.leftMouseClick((Boolean) playerEventObj.get("leftMouseClick"));
                playerBuilder.rightMouseClick((Boolean) playerEventObj.get("rightMouseClick"));

                JSONArray pressedKeysArray = (JSONArray) playerEventObj.get("pressedKeys");
                Set<String> keysList = new HashSet<>();
                for (Object key : pressedKeysArray) {
                    keysList.add(((String) key).toUpperCase());
                }
                playerBuilder.pressedKeys(keysList);

                PlayerEvent playerEvent = playerBuilder.build();
                playerIDToPlayerEvent.put(playerEvent.getID(), playerEvent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle parse error
        }
    }
}

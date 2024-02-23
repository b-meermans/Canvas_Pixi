package AopsTheater;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

public class Events {
    // TODO How do we want to actually handle multiplayer controls?
    private static int numUpdates;
    private static Integer defaultPlayer = 1;
    private static Map<Integer, PlayerEvent> playerIDToPlayerEvent;

    private static long elapsedTime;
    private static String month;
    private static int day;
    private static int year;
    private static String dayName;
    private static int hour;
    private static int minute;
    private static int second;

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

    private static void parseJSON(String jsonInput) {
        JsonObject rootObject = JsonParser.parseString(jsonInput).getAsJsonObject();

        numUpdates = rootObject.get("numberOfUpdates").getAsInt();
        elapsedTime = rootObject.get("elapsedTime").getAsLong();
        month = rootObject.get("month").getAsString();
        day = rootObject.get("day").getAsInt();
        year = rootObject.get("year").getAsInt();
        dayName = rootObject.get("dayName").getAsString();
        hour = rootObject.get("hour").getAsInt();
        minute = rootObject.get("minute").getAsInt();
        second = rootObject.get("second").getAsInt();

        JsonArray playerEventsArray = rootObject.getAsJsonArray("playerEvents");
        for (int i = 0; i < playerEventsArray.size(); i++) {
            JsonObject playerEventObj = playerEventsArray.get(i).getAsJsonObject();
            PlayerEvent.Builder playerBuilder = new PlayerEvent.Builder();

            playerBuilder.setID(playerEventObj.get("playerId").getAsInt());
            playerBuilder.mouseX(playerEventObj.get("mouseX").getAsDouble());
            playerBuilder.mouseY(playerEventObj.get("mouseY").getAsDouble());
            playerBuilder.leftMouseClick(playerEventObj.get("leftMouseClick").getAsBoolean());
            playerBuilder.rightMouseClick(playerEventObj.get("rightMouseClick").getAsBoolean());

            JsonArray pressedKeysArray = playerEventObj.getAsJsonArray("pressedKeys");
            Set<String> keysList = new HashSet<>();
            for (int j = 0; j < pressedKeysArray.size(); j++) {
                keysList.add(pressedKeysArray.get(j).getAsString().toUpperCase());
            }
            playerBuilder.pressedKeys(keysList);

            PlayerEvent playerEvent = playerBuilder.build();
            playerIDToPlayerEvent.put(playerEvent.getID(), playerEvent);
        }
    }

    public static String getMonth() {
        return month;
    }

    public static int getDay() {
        return day;
    }

    public static int getYear() {
        return year;
    }

    public static String getDayName() {
        return dayName;
    }

    public static int getHour() {
        return hour;
    }

    public static int getMinute() {
        return minute;
    }

    public static int getSecond() {
        return second;
    }

    public static long getElapsedTime() {
        return elapsedTime;
    }
}

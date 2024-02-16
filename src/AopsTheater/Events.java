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
        JSONParser parser = new JSONParser();

        try {
            JSONObject rootObject = (JSONObject) parser.parse(jsonInput);
            numUpdates = ((Long) rootObject.get("numberOfUpdates")).intValue();
            elapsedTime = (Long) rootObject.get("elapsedTime");
            month = (String) rootObject.get("month");
            day = ((Long) rootObject.get("day")).intValue();
            year = ((Long) rootObject.get("year")).intValue();
            dayName = (String) rootObject.get("dayName");
            hour = ((Long) rootObject.get("hour")).intValue();
            minute = ((Long) rootObject.get("minute")).intValue();
            second = ((Long) rootObject.get("second")).intValue();


            JSONArray playerEventsArray = (JSONArray) rootObject.get("playerEvents");
            for (Object o : playerEventsArray) {
                JSONObject playerEventObj = (JSONObject) o;
                PlayerEvent.Builder playerBuilder = new PlayerEvent.Builder();

                playerBuilder.setID(((Long) playerEventObj.get("playerId")).intValue());

                // JSON may come back as Longs, so annoying conversion situation
                double mouseX = ((Number) playerEventObj.get("mouseX")).doubleValue();
                playerBuilder.mouseX(mouseX);
                double mouseY = ((Number) playerEventObj.get("mouseY")).doubleValue();
                playerBuilder.mouseY(mouseY);

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

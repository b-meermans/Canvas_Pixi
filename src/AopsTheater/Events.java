package AopsTheater;

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

    public static boolean isKeyDown(String key) {
        return playerIDToPlayerEvent.get(defaultPlayer).isKeyPressed(key);
    }

    public static boolean isKeyDown(int playerID, String key) {
        key = key.toUpperCase();
        PlayerEvent playerEvent = playerIDToPlayerEvent.getOrDefault(playerID, null);
        return playerEvent != null && playerEvent.isKeyPressed(key);
    }

    public static void parseJSON(String jsonInput) {
        // TODO Need to figure this out for real.

//        numUpdates = 1;
//
//        HashSet<String> keys = new HashSet<>();
//        if (Math.random() < 0.2) {
//            keys.add("up");
//        }
//
//        // Create a new PlayerEvent and add it to the list
//        PlayerEvent playerEvent = new PlayerEvent.Builder()
//                .mouseX(500)
//                .mouseY(500)
//                .leftMouseClick(true)
//                .rightMouseClick(true)
//                .pressedKeys(keys)
//                .build();
//
//        playerIDToPlayerEvent.put(1, playerEvent);

        int lineIndex = -1;
        String[] lines = jsonInput.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("\"numberOfUpdates\":")) {
                String[] parts = line.split(":");
                numUpdates = Integer.parseInt(parts[1].trim().replace(",", ""));
            } else if (line.startsWith("\"playerEvents\":")) {
                while (!(line = lines[++lineIndex].trim()).equals("]")) {
                    if (line.equals("{")) {
                        PlayerEvent.Builder playerBuilder = new PlayerEvent.Builder();

                        while (!(line = lines[++lineIndex].trim()).startsWith("}")) {
                            String[] keyValue = line.split(":");
                            if (keyValue.length == 2) {
                                String key = keyValue[0].trim().replace("\"", "");
                                String value = keyValue[1].trim().replace("\"", "");

                                switch (key) {
                                    case "playerId":
                                        playerBuilder.setID(Integer.parseInt(value.replaceAll(",", "")));
                                        break;
                                    case "mouseX":
                                        playerBuilder.mouseX(Double.parseDouble(value.replaceAll(",", "")));
                                        break;
                                    case "mouseY":
                                        playerBuilder.mouseY(Double.parseDouble(value.replaceAll(",", "")));
                                        break;
                                    case "leftMouseClick":
                                        playerBuilder.leftMouseClick(Boolean.parseBoolean(value.replaceAll(",", "")));
                                        break;
                                    case "rightMouseClick":
                                        playerBuilder.rightMouseClick(Boolean.parseBoolean(value.replaceAll(",", "")));
                                        break;
                                    case "pressedKeys":
                                        String[] keysArray = value.substring(1, value.length() - 1).split(",");
                                        Set<String> keysList = new HashSet<>();
                                        for (String keyItem : keysArray) {
                                            keysList.add(keyItem.trim().replace("\"", ""));
                                        }
                                        playerBuilder.pressedKeys(keysList);
                                        break;
                                }
                            }
                        }
                        PlayerEvent playerEvent = playerBuilder.build();
                        playerIDToPlayerEvent.put(playerEvent.getID(), playerEvent);
                    }
                }
            }
        }
    }
}

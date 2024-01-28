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

    public static boolean isKeyPressed(String key) {
        return playerIDToPlayerEvent.get(defaultPlayer).isKeyPressed(key.toUpperCase());
    }

    public static boolean isKeyPressed(int playerID, String key) {
        key = key.toUpperCase();
        PlayerEvent playerEvent = playerIDToPlayerEvent.getOrDefault(playerID, null);
        return playerEvent != null && playerEvent.isKeyPressed(key);
    }

    public static void parseJSON(String jsonInput) {
        // TODO Really want to use a JSON library.

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
                                        Set<String> keysList = new HashSet<>();
                                        if (value.contains("]")) {
                                            break;
                                        }

                                        while (!(line = lines[++lineIndex].trim()).startsWith("]")) {
                                            keysList.add(line.replaceAll("[,\"]", "").toUpperCase());
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

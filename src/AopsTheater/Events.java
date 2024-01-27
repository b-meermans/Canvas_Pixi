package AopsTheater;

import java.util.*;

public class Events {

    private static int numUpdates;
    private static Integer defaultPlayer;
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

    public static PlayerEvent getPlayerEvent(int playerID) {
        return playerIDToPlayerEvent.getOrDefault(playerID, null);
    }

    private static void parseJSON(String json) {
        // TODO Switch to a proper JSON library. This code relies on a well formed JSON currently.

        String[] tokens = json.split("\"");

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("numberOfUpdates")) {
                numUpdates = Integer.parseInt(tokens[i + 2]);
            } else if (tokens[i].equals("playerId")) {
                int playerId = Integer.parseInt(tokens[i + 2]);

                // Used for single player theaters, the first player found becomes the 'default' player
                if (defaultPlayer == null) {
                    defaultPlayer = playerId;
                }

                double mouseX = Double.parseDouble(tokens[i + 8]);
                double mouseY = Double.parseDouble(tokens[i + 12]);
                boolean leftMouseClick = Boolean.parseBoolean(tokens[i + 16]);
                boolean rightMouseClick = Boolean.parseBoolean(tokens[i + 20]);

                Set<String> pressedKeys = new HashSet<>();
                if (tokens[i + 24].equals("pressedKeys")) {
                    i += 2; // Move to the beginning of the keys array
                    while (!tokens[i].equals("]")) {
                        pressedKeys.add(tokens[i].replaceAll(",", "")); // Remove commas
                        i++;
                    }
                }

                // Create a new PlayerEvent and add it to the list
                PlayerEvent playerEvent = new PlayerEvent.Builder()
                        .mouseX(mouseX)
                        .mouseY(mouseY)
                        .leftMouseClick(leftMouseClick)
                        .rightMouseClick(rightMouseClick)
                        .pressedKeys(pressedKeys)
                        .build();

                playerIDToPlayerEvent.put(playerId, playerEvent);
            }
        }
    }

}

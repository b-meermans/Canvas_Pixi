package AopsTheater;

public class AopsTheater {
    private static String startingStageClassName = "StudentCode.MyStage";
    private static AopsTheater instance;

    /**
     * Knows which stage is in use and conducts all actions necessary
     */
    private Director director;

    private AopsTheater() {
        reset();
    }

    /**
     * Creates the AopsTheater
     * @return the Theater object itself or null if the initial Stage failed
     */
    public static AopsTheater build() {
        // TODO Receive the starting Stage class name
        if (instance == null) {
            instance = new AopsTheater();

            // The starting stage failed to load, could not find the file
            if (instance.director == null) {
                System.out.printf("Could not load initial stage: %s%n", startingStageClassName);
                return null;
            }
        }


        return instance;
    }

    /**
     * Generates a JSON containing the method headers of a given UUID
     * @param uuid An actor's UUID or null for the Stage's methods
     * @return a json file containing the method headers
     */
    public String getMethodsJSON(String uuid) {
        return director.getMethodsJSON(uuid);
    }

    /**
     * Gets the state of the theater with no updates.
     * @return a json file representing the state of the theater or null if a failure occurred
     */
    public String getState() {
        return AopsTheaterHandler.getState(director);
    }

    /**
     * Asks a single theater piece to perform a specific method as defined in the json.
     * @param json Contains the actor UUID or null for stage UUID, the method name, and the parameters.
     * @return a json file representing the state of the theater or null if a failure occurred
     */
    public String invokeMethod(String json) {
        return AopsTheaterHandler.invokeMethod(director, json);
    }

    /**
     * Resets the Theater to its original state.
     * @return a json file representing the state of the theater or null if a failure occurred
     */
    public String reset() {
        director = AopsTheaterHandler.buildDirector(startingStageClassName);
        if (director == null) {
            return null;
        }

        Events.reset();
        return director.getState();
    }

    /**
     * Simulates a single step with no exterior events (mouse/keyboard).
     * @return a json file representing the state of the theater or null if the step failed
     */
    public String step() {
        // No exterior events will apply for this step
        Events.reset();

        return AopsTheaterHandler.update(director);
    }

    /**
     * Simulates one 'update' of the entire theater.
     * @param json A json including: numFrames and a list of all player's events
     * @return a json file representing the state of the theater or null if the update failed
     */
    public String update(String json) {
        // Convert the Json event information into an object
        //Events.fromJSON(json);

        return AopsTheaterHandler.update(director);
    }
}

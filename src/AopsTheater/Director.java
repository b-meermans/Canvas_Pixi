package AopsTheater;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Director {
    // TODO Future: Adjust design to allow for different stages
    private final Stage stage;

    public Director(String stageClassName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> stageType = Class.forName(stageClassName);
        Constructor<?> constructor = stageType.getConstructor();

        stage = (Stage) constructor.newInstance();
        stage.endAct();
    }

    public void update() {
        for (int actCount = 0; actCount < Events.getNumUpdates(); actCount++) {
            // TODO Need to check how sounds work over multiple acts
            performOneAct();
        }
    }

    void performOneAct() {
        stage.act();

        for (Actor actor : stage.getActors()) {
            actor.act();
        }

        stage.endAct();
    }

    JsonObject getState() {
        return JsonConversion.getStateJson(stage);
    }

    String getJsonStringState() {
        Gson gson = new Gson();
        return gson.toJson(getState());
    }

    AopsTheaterComponent getComponentByUUID(String uuid) {
        return stage.getComponentByUUID(uuid);
    }

    Stage getStage() {
        return stage;
    }

    String getMethodsJSON(String uuid) {
        if (uuid == null) {
            return MethodJSONBuilder.methodsToJSON(stage);
        }

        AopsTheaterComponent actor = getComponentByUUID(uuid);
        if (actor == null) {
            return null;
        }

        return MethodJSONBuilder.methodsToJSON(actor);
    }
}
package AopsTheater;

import JsonSimple.JSONObject;

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

    JSONObject getState() {
        return JsonConversion.getStateJson(stage);
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
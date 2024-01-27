package AopsTheater;

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

    String getState() {
        return JsonConversion.getStateJson(stage);
    }


    Actor getSpriteByUUID(String uuid) {
        return stage.getActorByUUID(uuid);
    }

    String getMethodsJSON(String uuid) {
        if (uuid == null) {
            return MethodJSONBuilder.methodsToJSON(stage);
        }

        Actor actor = getSpriteByUUID(uuid);
        if (actor == null) {
            return null;
        }

        return MethodJSONBuilder.methodsToJSON(actor);
    }

    String invokeMethod(String json) {
        return getState();
    }
}
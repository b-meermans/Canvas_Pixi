package AopsGui;

import AopsGui.Exceptions.TimeLimitExceededException;
import AopsGui.Exceptions.StackOverflowError;
import StudentCode.*;

public class Aops2DRunner {

    private static final int MAX_STACK_TRACE_LINES = 50;
    private static final long MAX_RUN_TIME = 3000;
    private static final int COUNTER_LIMIT = 5000;

    private static final int MAX_METHODS_ON_STACK = 2500;

    private static int methodStackCount;
    private static long clockStart;
    private static long clockCounter;
    private final Stage stage;

    public Aops2DRunner() {
        stage = new MyStage();
        stage.endAct();
    }

    /**
     * This produces one 'step' of actions from all involved graphical components.
     * @param mouseX the mouse x coordinate
     * @param mouseY the mouse y coordinate
     * @param isMousePressed true if the mouse is currently being pressed
     * @param keysPressed an array containing all key names that are pressed
     * @return a JSON file containing all actors to update or null if the program should stop
     */
    public String act(double mouseX, double mouseY, boolean isMousePressed, String[] keysPressed) {

        Gui.updateKeys(keysPressed);
        Gui.updateMouse(mouseX, mouseY, isMousePressed);

        try {
            methodStackCount = 0;
            clockStart = System.currentTimeMillis();
            clockCounter = 0;

            stage.act();

            for (Actor actor : stage.getActors()) {
                actor.act();
            }

            stage.endAct();
        } catch (Exception e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            if (stackTrace.length > MAX_STACK_TRACE_LINES) {
                for (int i = 0; i < MAX_STACK_TRACE_LINES; i++) {
                    System.err.println(stackTrace[i]);
                }

                System.err.println("Cutting off additional method calls.");
            } else {
                e.printStackTrace();
            }

            return null;
        }

        // TODO This JSON needs to be more than just actors
        return JsonConversion.getActorJason(stage.getActors());
    }

    public static void exitIfNeeded() {
        clockCounter++;
        if (clockCounter > COUNTER_LIMIT) {
            if (System.currentTimeMillis() - clockStart > MAX_RUN_TIME) {
                throw new TimeLimitExceededException();
            }
            clockCounter = 0;
        }
    }

    public static void addMethodToStack() {
        methodStackCount++;
        if (methodStackCount > MAX_METHODS_ON_STACK) {
            throw new StackOverflowError();
        }
    }

    public static void removeMethodFromStack() {
        methodStackCount--;
    }

    public Stage getStage() {
        return stage;
    }

    public String getActors() {
        return JsonConversion.getActorJason(stage.getActors());
    }
}

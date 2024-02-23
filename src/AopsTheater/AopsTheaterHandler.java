package AopsTheater;

import com.google.gson.JsonObject;

import java.util.Stack;

public class AopsTheaterHandler {
    // TODO The Z coordinate is not being used yet for layering objects (probably better in director)

    /**
     * Holds the current line number executing of the student's code
     */
    private static final Stack<Integer> stack = new Stack<>();
    /**
     * How many lines to display in a stack trace
     */
    private static final int MAX_STACK_TRACE_LINES = 50;
    /**
     * How long, in milliseconds, a second update can run before it is timed out
     */
    private static final long MAX_RUN_TIME = 3000;
    /**
     * How many line calls before checking on the clock again, to reduce calls to the clock
     */
    private static final int COUNTER_LIMIT = 5000;
    /**
     * Max number of student methods on the stack before the update is considered a stackoverflow.
     */
    private static final int MAX_METHODS_ON_STACK = 2000;
    // TODO Re-think how Z values work for reordering items later.
    private static int currentZ = 0;
    /**
     * When did the update start?
     */
    private static long clockStart;

    /**
     * How many line calls have occurred this update?
     */
    private static long clockCounter;

    public static void addMethodToStack(int lineNumber) {
        stack.push(lineNumber);
        if (stack.size() > MAX_METHODS_ON_STACK) {
            throw new StackOverflowError();
        }
    }

    public static void changeStatement(int lineNumber) {
        stack.pop();
        stack.push(lineNumber);
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

    static Director buildDirector(String startingStageClassName) {
        resetTimers();
        currentZ = 0;

        try {
            return new Director(startingStageClassName);
        } catch (Exception e) {
            printStackTrace(e);
            return null;
        }
    }

    static String getState(Director director) {
        resetTimers();

        try {
            return director.getJsonStringState();
        } catch (Exception e) {
            printStackTrace(e);
            return null;
        }
   }

   static JsonObject invokeMethod(Director director, String json) {
       resetTimers();

       JsonObject result = JsonHandler.invokeMethod(director, json);
       return result;
   }

    static int nextZ() {
        return currentZ++;
    }

    private static void printStackTrace(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i = 0; i < MAX_STACK_TRACE_LINES && i < stackTrace.length; i++) {
            String trace = stackTrace[i].toString();
            if (!stack.isEmpty() && trace.startsWith("StudentCode.")) {
                trace = trace.replace("Unknown Source", stack.pop().toString());
            } else {
                trace = trace.replace("(Unknown Source)", "");
                trace = trace.replace(".<init>", "");
            }
            System.err.println(trace);
        }
    }

    public static void removeMethodFromStack() {
        if (!stack.isEmpty()) {
            stack.pop();
        }
    }

   static void resetTimers() {
       // Initialize the states for this update to check runtime and method calls on the stack
       clockStart = System.currentTimeMillis();
       clockCounter = 0;
       stack.clear();
   }

    static String update(Director director) {
        resetTimers();

        try {
            director.update();
            String result = director.getJsonStringState();
            return result;
        } catch (Exception e) {
            printStackTrace(e);
            return null;
        }
    }
}

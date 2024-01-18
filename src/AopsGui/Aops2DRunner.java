package AopsGui;

import StudentCode.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class Aops2DRunner {

    private static final Stack<Integer> stack = new Stack<>();
    private static final int MAX_STACK_TRACE_LINES = 50;
    private static final long MAX_RUN_TIME = 3000;
    private static final int COUNTER_LIMIT = 5000;

    private static final int MAX_METHODS_ON_STACK = 2000;

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
    public String act(int actsToSimulate, double mouseX, double mouseY, boolean isMousePressed, String[] keysPressed) {

        Gui.updateKeys(keysPressed);
        Gui.updateMouse(mouseX, mouseY, isMousePressed);

        clockStart = System.currentTimeMillis();
        clockCounter = 0;
        stack.clear();

        try {
            for (int actCount = 0; actCount < actsToSimulate; actCount++) {
                performOneAct();
            }
        } catch (Exception e) {
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

            return null;
        }

        // TODO This JSON needs to be more than just actors
        return JsonConversion.getActorJason(stage.getActors());
    }

    public void performOneAct() {
        stage.act();

        for (Actor actor : stage.getActors()) {
            actor.act();
        }

        stage.endAct();
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

    public static void removeMethodFromStack() {
        if (!stack.isEmpty()) {
            stack.pop();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public String getActors() {
        return JsonConversion.getActorJason(stage.getActors());
    }

    public void trial() {
        Actor a = stage.getActors().get(0);
        System.out.println(getMethodInformation(a.uuid));
    }

    public String getMethodInformation(String uuid) {
        Actor actor = stage.getActorByUUID(uuid);
        if (actor == null) {
            return null;
        }

        List<String> allMethods = getMethodInformation(actor);
        for (String method : allMethods) {
            if (method.contains("(")) {
                System.out.printf("\t%s%n", method);
            } else {
                System.out.printf("%n\uD83D\uDCD8 %s%n", method);
            }
        }

        return allMethods.toString();
    }

    public static List<String> getMethodInformation(Object object) {
        Class<?> clazz = object.getClass();
        List<String> methodInformationList = new ArrayList<>();

        // Get all methods, including inherited methods
        Map<String, List<String>> classMethodsMap = new LinkedHashMap<>();
        Set<String> uniqueMethods = new HashSet<>();
        getAllMethods(clazz, classMethodsMap, uniqueMethods);

        for (Map.Entry<String, List<String>> entry : classMethodsMap.entrySet()) {
            methodInformationList.add(entry.getKey());  // Add class name as header
            methodInformationList.addAll(entry.getValue());  // Add methods
        }

        return methodInformationList;
    }

    private static void getAllMethods(Class<?> clazz, Map<String, List<String>> classMethodsMap, Set<String> uniqueMethods) {
        while (clazz != null) {
            List<String> methodsList = new ArrayList<>();
            Method[] declaredMethods = clazz.getDeclaredMethods();
            // Sort the declaredMethods array based on return type then method names
            Arrays.sort(declaredMethods, Comparator
                    .<Method, String>comparing(m -> m.getReturnType().getSimpleName().toLowerCase())
                    .thenComparing(Method::getName, String.CASE_INSENSITIVE_ORDER));



            for (Method method : declaredMethods) {
                String returnType = method.getReturnType().getSimpleName();
                String methodName = method.getName();
                Parameter[] parameters = method.getParameters();
                StringBuilder methodSignature = new StringBuilder(returnType + " " + methodName + "(");

                for (int j = 0; j < parameters.length; j++) {
                    Parameter parameter = parameters[j];
                    methodSignature.append(parameter.getType().getSimpleName());

                    if (j < parameters.length - 1) {
                        methodSignature.append(", ");
                    }
                }

                methodSignature.append(")");
                String methodSig = methodSignature.toString();

                // Don't re-display overridden methods
                if (!uniqueMethods.contains(methodSig)) {
                    uniqueMethods.add(methodSig);
                    methodsList.add(methodSig);
                }
            }

            classMethodsMap.put(clazz.getSimpleName(), methodsList);
            clazz = clazz.getSuperclass();
        }
    }
}

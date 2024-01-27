package AopsTheater;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class MethodJSONBuilder {
    public static String methodsToJSON(Object object) {
        StringBuilder jsonBuilder = new StringBuilder("{");

        Class<?> clazz = object.getClass();
        Set<String> methodSignatures = new HashSet<>();

        while (clazz != null) {
            jsonBuilder.append("\"").append(getSimpleClassName(clazz)).append("\": {");

            Method[] methods = clazz.getDeclaredMethods();
            Arrays.stream(methods)
                    .filter(method -> Modifier.isPublic(method.getModifiers()))
                    .sorted(Comparator.comparing(Method::getName, String.CASE_INSENSITIVE_ORDER))
                    .forEach(method -> {
                        String methodSignature = getMethodSignature(method);
                        if (!methodSignatures.contains(methodSignature)) {
                            jsonBuilder.append("\"").append(method.getName()).append("\": {");
                            jsonBuilder.append("\"returnType\": \"").append(getTypeString(method.getReturnType())).append("\", ");
                            jsonBuilder.append("\"parameterTypes\": [");
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            for (int i = 0; i < parameterTypes.length; i++) {
                                jsonBuilder.append("\"").append(getTypeString(parameterTypes[i])).append("\"");
                                if (i < parameterTypes.length - 1) {
                                    jsonBuilder.append(", ");
                                }
                            }
                            jsonBuilder.append("]");
                            jsonBuilder.append("}, ");
                            methodSignatures.add(methodSignature);
                        }
                    });

            if (jsonBuilder.charAt(jsonBuilder.length() - 2) == ',') {
                jsonBuilder.deleteCharAt(jsonBuilder.length() - 2);
            }

            jsonBuilder.append("}, ");
            clazz = clazz.getSuperclass();
        }

        if (jsonBuilder.charAt(jsonBuilder.length() - 2) == ',') {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 2);
        }

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    private static String getSimpleClassName(Class<?> clazz) {
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf('.');
        return (lastDotIndex == -1) ? className : className.substring(lastDotIndex + 1);
    }

    private static String getTypeString(Class<?> type) {
        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            return getSimpleClassName(componentType) + "[]";
        } else {
            return getSimpleClassName(type);
        }
    }

    private static String getMethodSignature(Method method) {
        StringBuilder signature = new StringBuilder(method.getName() + "(");
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            signature.append(getTypeString(parameterTypes[i]));
            if (i < parameterTypes.length - 1) {
                signature.append(",");
            }
        }
        signature.append(")");
        return signature.toString();
    }
}

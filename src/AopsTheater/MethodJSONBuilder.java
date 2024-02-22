package AopsTheater;

import com.google.gson.Gson;

import java.lang.reflect.*;
import java.util.*;

public class MethodJSONBuilder {
    private static final Gson gson = new Gson();

    public static String methodsToJSON(Object object) {
        Map<String, Map<String, MethodDetails>> classMethods = new LinkedHashMap<>();

        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Map<String, MethodDetails> methodsJson = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

            for (Method method : clazz.getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers())) {
                    String methodName = method.getName();
                    MethodDetails methodDetails = new MethodDetails(method);
                    methodsJson.put(methodName, methodDetails);
                }
            }

            if (!methodsJson.isEmpty()) {
                classMethods.put(getSimpleClassName(clazz), methodsJson);
            }
            clazz = clazz.getSuperclass();
        }

        return gson.toJson(classMethods);
    }

    private static String getSimpleClassName(Class<?> clazz) {
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf('.');
        return (lastDotIndex == -1) ? className : className.substring(lastDotIndex + 1);
    }

    private static class MethodDetails {
        String returnType;
        List<String> parameterTypes;

        MethodDetails(Method method) {
            this.returnType = getTypeString(method.getGenericReturnType());
            this.parameterTypes = new ArrayList<>();
            for (Type type : method.getGenericParameterTypes()) {
                this.parameterTypes.add(getTypeString(type));
            }
        }
    }

    private static String getTypeString(Type type) {
        if (type instanceof Class<?>) {
            Class<?> typeClass = (Class<?>) type;
            if (typeClass.isArray()) {
                return getSimpleClassName(typeClass.getComponentType()) + "[]";
            } else {
                return getSimpleClassName(typeClass);
            }
        } else if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] typeArguments = paramType.getActualTypeArguments();
            StringJoiner typeArgsString = new StringJoiner(", ");

            for (Type argType : typeArguments) {
                typeArgsString.add(argType.getTypeName());
            }

            return getSimpleClassName((Class<?>) paramType.getRawType()) + "<" + typeArgsString + ">";
        } else if (type instanceof TypeVariable) {
            // Handle generic type variables (like T in List<T>)
            TypeVariable<?> typeVar = (TypeVariable<?>) type;
            return typeVar.getName();
        } else {
            return type.toString();
        }
    }
}

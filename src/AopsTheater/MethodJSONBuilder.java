package AopsTheater;

import JsonSimple.JSONArray;
import JsonSimple.JSONObject;

import java.lang.reflect.*;
import java.util.*;

public class MethodJSONBuilder {
    public static String methodsToJSON(Object object) {
        // TODO Sort by Class type and then Method name alphabetically
        JSONObject classMethods = new JSONObject();

        Class<?> clazz = object.getClass();
        Set<String> methodSignatures = new HashSet<>();

        while (clazz != null) {
            JSONObject methodsJson = new JSONObject();

            Method[] methods = clazz.getDeclaredMethods();
            Arrays.stream(methods)
                    .filter(method -> Modifier.isPublic(method.getModifiers()))
                    .sorted(Comparator.comparing(Method::getName, String.CASE_INSENSITIVE_ORDER))
                    .forEach(method -> {
                        String methodSignature = getMethodSignature(method);
                        if (!methodSignatures.contains(methodSignature)) {
                            JSONObject methodDetails = new JSONObject();
                            methodDetails.put("returnType", getTypeString(method.getGenericReturnType()));

                            JSONArray parameterTypesArray = new JSONArray();
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            for (Class<?> paramType : parameterTypes) {
                                parameterTypesArray.add(getTypeString(paramType));
                            }
                            methodDetails.put("parameterTypes", parameterTypesArray);

                            methodsJson.put(method.getName(), methodDetails);
                            methodSignatures.add(methodSignature);
                        }
                    });

            classMethods.put(getSimpleClassName(clazz), methodsJson);
            clazz = clazz.getSuperclass();
        }

        return classMethods.toJSONString();
    }

    private static String getSimpleClassName(Class<?> clazz) {
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf('.');
        return (lastDotIndex == -1) ? className : className.substring(lastDotIndex + 1);
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

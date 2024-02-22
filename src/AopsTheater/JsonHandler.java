package AopsTheater;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class JsonHandler {
    private static final Gson gson = new Gson();

    static JsonObject invokeMethod(Director director, String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        String uuid = jsonObject.get("UUID").getAsString();
        Object targetObject = director.getComponentByUUID(uuid);
        if (targetObject == null) {
            targetObject = director.getStage();
        }

        String methodName = jsonObject.get("method").getAsString();
        JsonArray parameters = jsonObject.getAsJsonArray("parameters");
        List<Class<?>> types = new ArrayList<>();
        List<Object> args = new ArrayList<>();

        for (int i = 0; i < parameters.size(); i++) {
            JsonObject paramObject = parameters.get(i).getAsJsonObject();
            String type = paramObject.get("type").getAsString();
            Object value = gson.fromJson(paramObject.get("value"), Object.class);

            switch (type) {
                case "int":
                    types.add(int.class);
                    args.add(((Number) value).intValue());
                    break;
                case "Integer":
                    types.add(Integer.class);
                    args.add(((Number) value).intValue());
                    break;
                case "double":
                    types.add(double.class);
                    args.add(((Number) value).doubleValue());
                    break;
                case "Double":
                    types.add(Double.class);
                    args.add(((Number) value).doubleValue());
                    break;
                case "float":
                    types.add(float.class);
                    args.add(((Number) value).floatValue());
                    break;
                case "Float":
                    types.add(Float.class);
                    args.add(((Number) value).floatValue());
                    break;
                case "short":
                    types.add(short.class);
                    args.add(((Number) value).shortValue());
                    break;
                case "Short":
                    types.add(Short.class);
                    args.add(((Number) value).shortValue());
                    break;
                case "long":
                    types.add(long.class);
                    args.add(((Number) value).longValue());
                    break;
                case "Long":
                    types.add(Long.class);
                    args.add(((Number) value).longValue());
                    break;
                case "byte":
                    types.add(byte.class);
                    args.add(((Number) value).byteValue());
                    break;
                case "Byte":
                    types.add(Byte.class);
                    args.add(((Number) value).byteValue());
                    break;
                case "char":
                    types.add(char.class);
                    args.add(value);
                    break;
                case "Character":
                    types.add(Character.class);
                    args.add(value);
                    break;
                case "boolean":
                    types.add(boolean.class);
                    args.add(value);
                    break;
                case "Boolean":
                    types.add(Boolean.class);
                    args.add(value);
                    break;
                default:
                    try {
                        types.add(Class.forName(type));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    args.add(value);

            }
        }

        try {
            Class<?>[] parameterTypes = new Class<?>[types.size()];
            parameterTypes = types.toArray(parameterTypes);

            Method method = targetObject.getClass().getMethod(methodName, parameterTypes);
            Class<?> returnType = method.getReturnType();

            Object result = method.invoke(targetObject, args.toArray());
            JsonObject resultJson = director.getState(); // Assuming getState returns a JsonObject

            if (returnType != Void.TYPE) {
                if (result instanceof AopsTheaterComponent) {
                    resultJson.addProperty("returned_uuid", ((AopsTheaterComponent) result).getUUID());
                } else {
                    resultJson.addProperty("returned", gson.toJson(result));
                }
            }

            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as required
            // For instance, return an empty JsonObject or a JsonObject with error info
            JsonObject errorJson = new JsonObject();
            errorJson.addProperty("error", "Method invocation failed: " + e.getMessage());
            return errorJson;
        }
    }
}

package AopsTheater;

import JsonSimple.JSONArray;
import JsonSimple.JSONObject;
import JsonSimple.parser.JSONParser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class JsonHandler {
    static JSONObject invokeMethod(Director director, String json) {
        JSONParser parser = new JSONParser();

        String uuid = null;
        String methodName = null;
        Object targetObject = null;
        List<Class<?>> types = new ArrayList<>();
        List<Object> args = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(json);

            uuid = (String) jsonObject.get("UUID");
            targetObject = director.getSpriteByUUID(uuid);
            if (targetObject == null) {
                targetObject = director.getStage();
            }

            methodName = (String) jsonObject.get("method");
            JSONArray parameters = (JSONArray) jsonObject.get("parameters");

            for (Object parameter : parameters) {
                JSONObject paramObject = (JSONObject) parameter;
                String type = (String) paramObject.get("type");
                Object value = paramObject.get("value");

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
                        types.add(Class.forName(type));
                        args.add(value);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


        try {
            Class<?>[] parameterTypes = new Class<?>[types.size()];
            for (int i = 0; i < args.size(); i++) {
                parameterTypes[i] = types.get(i);
            }

            Method method = targetObject.getClass().getMethod(methodName, parameterTypes);
            Class returnType = method.getReturnType();

            if (returnType.equals(Void.TYPE)) {
                // Nothing to add as a result to the JSON
                method.invoke(targetObject, args.toArray());
                return director.getState();
            } else {
                // Something was returned, we'll get a String representation to add to the JSON
                Object result = method.invoke(targetObject, args.toArray());
                JSONObject jsonObject = director.getState();

                // If the return was something with a UUID, we'll add a UUID to the json, otherwise, just the String
                if (result instanceof AopsTheaterComponent) {
                    jsonObject.put("returned_uuid", ((AopsTheaterComponent) result).getUUID());
                }
                else {
                    jsonObject.put("returned", result.toString());
                }

                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

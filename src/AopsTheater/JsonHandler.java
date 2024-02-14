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
                    case "Integer":
                        args.add((Integer) value);
                        break;
                    case "double":
                    case "Double:":
                        args.add((Double) value);
                        break;
                    case "float":
                    case "Float":
                        args.add((Float) value);
                        break;
                    case "short":
                    case "Short":
                        args.add((Short) value);
                        break;
                    case "long":
                    case "Long":
                        args.add((Long) value);
                        break;
                    case "byte":
                    case "Byte":
                        args.add((Byte) value);
                        break;
                    case "char":
                    case "Character":
                        args.add((Character) value);
                        break;
                    case "boolean":
                    case "Boolean":
                        args.add((Boolean) value);
                        break;
                    default:
                        args.add(value);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


        try {
            Method method = targetObject.getClass().getMethod(methodName, args.toArray(new Class[0]));
            Class returnType = method.getReturnType();

            if (returnType.equals(Void.TYPE)) {
                // Nothing to add as a result to the JSON
                method.invoke(targetObject, args.toArray());
                return director.getState();
            } else {
                // Something was returned, we'll get a String representation to add to the JSON
                Object result = method.invoke(targetObject, args.toArray());

                JSONObject jsonObject = director.getState();

                if (result instanceof AopsTheaterComponent) {
                    jsonObject.put("returned_uuid", ((AopsTheaterComponent) result).getUUID());
                }
                else {
                    jsonObject.put("returned", result.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

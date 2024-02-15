import AopsTheater.*;
import JsonSimple.JSONArray;
import JsonSimple.JSONObject;
import JsonSimple.parser.JSONParser;
import JsonSimple.parser.ParseException;
import StudentCode.Balloon;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        AopsTheater aopsTheater = AopsTheater.build();


        TheaterArt.drawEllipse(0, 0, 100, 200.5);
        TheaterArt.drawFilledEllipse(0, 0, 100, 200.5);
        TheaterArt.drawLine(30, 40, -20, 50);
        TheaterArt.setBorderColor(Color.GREEN);
        TheaterArt.drawFilledRectangle(1, 1, 3, 3);
        TheaterArt.drawRoundedRectangle(1, 1, 3, 3, 1);

        double[] xs = {0, 5, 19, 100};
        double[] ys = {40, 20, 10};
        TheaterArt.drawFilledPolygon(xs, ys);


        System.out.println(aopsTheater.getState());
//        System.out.println(aopsTheater.getMethodsJSON("A"));

//
//        String json = "{\n" +
//                "  \"numberOfUpdates\": 1,\n" +
//                "  \"playerEvents\": [\n" +
//                "    {\n" +
//                "      \"playerId\": 1,\n" +
//                "      \"mouseX\": 440.5,\n" +
//                "      \"mouseY\": 590.265625,\n" +
//                "      \"leftMouseClick\": false,\n" +
//                "      \"rightMouseClick\": false,\n" +
//                "      \"pressedKeys\": [\n" +
//                "        \"3\",\n" +
//                "        \"4\",\n" +
//                "        \"5\"\n" +
//                "      ]\n" +
//                "    }\n," +
//                "    {\n" +
//                "      \"playerId\": 1,\n" +
//                "      \"mouseX\": 2.5,\n" +
//                "      \"mouseY\": 590.265625,\n" +
//                "      \"leftMouseClick\": false,\n" +
//                "      \"rightMouseClick\": false,\n" +
//                "      \"pressedKeys\": [\n" +
//                "        \"3\",\n" +
//                "        \"4\",\n" +
//                "        \"5\"\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//
//        String j = "{\n" +
//                "  \"UUID\": \"E28394818123128\",\n" +
//                "  \"method\": \"move\",\n" +
//                "  \"returnType\": \"void\",\n" +
//                "  \"parameters\": [\n" +
//                "    {\n" +
//                "      \"type\": \"double\",\n" +
//                "      \"value\": 45.2\n" +
//                "    }" +
//                "  ]\n" +
//                "}";
//
//        aopsTheater.invokeMethod("{\n" +
//                "  \"UUID\": \"A\",\n" +
//                "  \"method\": \"setLocation\",\n" +
//                "  \"parameters\": [\n" +
//                "    {\n" +
//                "      \"type\": \"double\",\n" +
//                "      \"value\": 200\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"type\": \"double\",\n" +
//                "      \"value\": 100\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}");

        String result = aopsTheater.invokeMethod("{\n" +
                "  \"UUID\": \"A\",\n" +
                "  \"method\": \"getX\",\n" +
                "  \"parameters\": []\n" +
                "}");

        //System.out.println(result);

    }

    static void invokeMethod(String json) {

        JSONParser parser = new JSONParser();

        String uuid = null;
        String returnType = null;
        String methodName = null;
        Object targetObject = null;
        List<Class<?>> types = new ArrayList<>();
        List<Object> args = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(json);

            uuid = (String) jsonObject.get("UUID");
            Balloon b = new Balloon();
            b.setLocation(1, 50);
            targetObject = b;

            returnType = (String) jsonObject.get("returnType");
            methodName = (String) jsonObject.get("method");
            JSONArray parameters = (JSONArray) jsonObject.get("parameters");


            for (Object parameter : parameters) {
                JSONObject paramObject = (JSONObject) parameter;
                String type = (String) paramObject.get("type");
                Object value = paramObject.get("value");

                switch (type) {
                    case "int":
                        types.add(int.class);
                        args.add((Integer) value);
                        break;
                    case "Integer":
                        types.add(Integer.class);
                        args.add((Integer) value);
                        break;
                    case "double":
                        types.add(double.class);
                        args.add((Double) value);
                        break;
                    case "Double":
                        types.add(Double.class);
                        args.add((Double) value);
                        break;
                    case "float":
                        types.add(float.class);
                        args.add((Float) value);
                        break;
                    case "Float":
                        types.add(Float.class);
                        args.add((Float) value);
                        break;
                    case "short":
                        types.add(short.class);
                        args.add((Short) value);
                        break;
                    case "Short":
                        types.add(Short.class);
                        args.add((Short) value);
                        break;
                    case "long":
                        types.add(long.class);
                        args.add((Long) value);
                        break;
                    case "Long":
                        types.add(Long.class);
                        args.add((Long) value);
                        break;
                    case "byte":
                        types.add(byte.class);
                        args.add((Byte) value);
                        break;
                    case "Byte":
                        types.add(Byte.class);
                        args.add((Byte) value);
                        break;
                    case "char":
                        types.add(char.class);
                        args.add((Character) value);
                        break;
                    case "Character":
                        types.add(Character.class);
                        args.add((Character) value);
                        break;
                    case "boolean":
                        types.add(boolean.class);
                        args.add((Boolean) value);
                        break;
                    case "Boolean":
                        types.add(Boolean.class);
                        args.add((Boolean) value);
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
            System.out.println(args);

            Class<?>[] parameterTypes = new Class<?>[types.size()];
            for (int i = 0; i < args.size(); i++) {
                parameterTypes[i] = types.get(i);
            }

            Method method = targetObject.getClass().getMethod(methodName, parameterTypes);

            if ("void".equals(returnType)) {
                method.invoke(targetObject, args.toArray());
                System.out.println(((Actor)targetObject).getX());
            } else {
                Object result = method.invoke(targetObject, args.toArray());
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

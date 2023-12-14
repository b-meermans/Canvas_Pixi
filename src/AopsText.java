//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class AopsText {

    public AopsText() {
        System.out.println("Loaded up Aops2d Demo");
    }


    public static void testIndexNegativeOne() {
        int[] array = {};
        try {
            System.out.println(array[-1] + ": should have thrown an exception.");
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Catch on index -1 worked.");
            e.printStackTrace();
        }
    }

    public static void testOOBException() {
        int[] array = {3, 1, 4};
        try {
            System.out.println(array[-2] + ": should have thrown an exception.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Catch on index -2 worked.");
            e.printStackTrace();
        }
    }

    public static void testNullPointerException() {
        String string = null;
        try {
            System.out.println(string.length() + ": should have thrown an exception.");
        } catch (NullPointerException e) {
            System.out.println("Catch on null pointer exception worked.");
            e.printStackTrace();
        }
    }

    public static void testArithmeticException() {
        try {
            System.out.println(1 / 0 + ": should have thrown an exception.");
        } catch (ArithmeticException e) {
            System.out.println("Catch on arithmetic exception worked.");
        }
    }

    public static void testMultipleExceptions() {
        int[] array = {3, 1, 4};
        try {
            System.out.println(array[-2] + ": should have thrown an exception.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Catch on index -2 worked.");
            e.printStackTrace();
        }

        try {
            System.out.println(array[3] + ": should have thrown an exception.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Catch on index 3 worked.");
            e.printStackTrace();
        }
    }

    public static void testClassCastException() {
        Object obj = 3;

        try {
            String string = (String) obj;
            System.out.println(string + ": should have thrown an exception.");
        } catch (ClassCastException e) {
            System.out.println("Catch on cast cast exception index worked.");
            e.printStackTrace();
        }
    }

}

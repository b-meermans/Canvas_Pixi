import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner2D {
    private final List<Actor> actors;
    private static double mouseX;
    private static double mouseY;

    // private OnlineInputStream inputStream;
    private static Scanner scanner;
    private static DynamicInputStream dynamicInputStream;

    public Runner2D() {
        setupInputStream();

        scanner = new Scanner(System.in);

        actors = new ArrayList<>();

        for (int i = 0; i < 50; i += 4) {
            actors.add(new CircleMover(i, 80));
            actors.add(new CircleMover(i, 160));
            actors.add(new Walker(0, i));
            actors.add(new Follower(300, i * 4));
        }

        scanner = new Scanner(System.in);
    }

    public void setupInputStream() {
        dynamicInputStream = new DynamicInputStream();
        System.setIn(dynamicInputStream);
        scanner = new Scanner(System.in);
    }

    public void updateInputStream(String[] values) {
        for (String str : values) {
            dynamicInputStream.addText(str);
        }
    }

    public void act(double mouseX, double mouseY) {
        Runner2D.mouseX = mouseX;
        Runner2D.mouseY = mouseY;

        for (Actor a : actors) {
            a.act();
        }


       System.out.println(scanner.nextLine());

    }

    public List<Actor> getActor() {
        return actors;
    }

    public double[] getX() {
        double[] xs = new double[2];
        for (int i = 0; i < xs.length; i++) {
            xs[i] = actors.get(i).getX();
        }
        return xs;
    }

    public static double getMouseX() {
        return Runner2D.mouseX;
    }

    public static double getMouseY() {
        return Runner2D.mouseY;
    }

    public static void main(String[] args) {
        Runner2D runner = new Runner2D();
        while (true) {
            runner.act(0, 0);
            dynamicInputStream.print();
            String line = scanner.nextLine();
            System.out.println("Read from Custom System.in: " + line);
            try {
                // Sleep for a while before checking for new content
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

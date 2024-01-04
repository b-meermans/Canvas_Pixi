package StudentCode;

import AopsGui.Actor;
import AopsGui.Gui;

public class ImageChanger extends Actor {
    private String[] images = {"balloon.png", "plane.png", "AoPS.png"};
    private static final long MIN_TIME_CHANGE = 100;
    private int currentIndex;
    private long lastUpdate = System.currentTimeMillis();

    public ImageChanger() {
        setImage(images[currentIndex]);
    }

    public void act() {
        if (System.currentTimeMillis() - lastUpdate < MIN_TIME_CHANGE) {
            return;
        }

        if (Gui.isKeyPressed("1")) {
            changeImage(currentIndex + 1);
        } else if (Gui.isKeyPressed("2")) {
            changeImage(currentIndex - 1);
        }
    }

    public void changeImage(int index) {
//        if (index < 0) {
//            index = images.length - 1;
//        }
//        index %= images.length;
        setImage(images[index]);
        currentIndex = index;

        lastUpdate = System.currentTimeMillis();
        System.out.println("Current image: " + currentIndex);
    }
}

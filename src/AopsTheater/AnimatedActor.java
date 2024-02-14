package AopsTheater;

public class AnimatedActor extends Actor {
    // TODO Change to a different default image
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/9/98/New-Bouncywikilogo.gif";

    private boolean isPlaying = true;

    public AnimatedActor() {
        this(DEFAULT_IMAGE);
    }

    public AnimatedActor(String image) {
        super(image);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void playAnimation() {
        isPlaying = true;
    }

    public void stopAnimation() {
        isPlaying = false;
    }
}

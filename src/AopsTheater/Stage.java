package AopsTheater;

import java.util.ArrayList;
import java.util.List;

public abstract class Stage extends AopsTheaterComponent {
    // TODO Add in removing objects

    private static final String DEFAULT_IMAGE = "dots.png";

    private final int width;
    private final int height;
    private String image;

    private transient final List<Actor> actors;
    private transient final List<Actor> addedActors;
    private transient final List<Text> texts;
    private transient final List<Sound> sounds;

    public Stage(int width, int height) {
        this(width, height, DEFAULT_IMAGE);
    }

    public Stage(int width, int height, String imageName) {
        this.width = width;
        this.height = height;
        this.image = imageName;
        actors = new ArrayList<>();
        addedActors = new ArrayList<>();

        texts = new ArrayList<Text>();
        sounds = new ArrayList<Sound>();
    }

    public void act() {

    }

    public void addActor(Actor actor, double x, double y) {
        addedActors.add(actor);
        actor.setLocation(x, y);
        actor.setStage(this);
    }

    public void addText(Text text, double x, double y) {
        texts.add(text);
        text.setLocation(x, y);
        text.setStage(this);
    }

    public void addSound(String filename) {
        sounds.add(new Sound(filename));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Text> getTexts() {
        return texts;
    }

    public List<Sound> getSounds() {
        return sounds;
    }



    void endAct() {
        actors.addAll(addedActors);
        addedActors.clear();
    }

    AopsTheaterComponent getComponentByUUID(String uuid) {
        // TODO All UUIDs go into a Map - make this much quicker

        if (uuid.equals(getUUID())) {
            return this;
        }

        for (Actor a: actors) {
            if (uuid.equals(a.getUUID())) {
                return a;
            }
        }

        for (Text t: texts) {
            if (uuid.equals(t.getUUID())) {
                return t;
            }
        }

        return null;
    }

    // TODO Objects in Range?
    // TODO Get all Objects (Text, Actor)

}

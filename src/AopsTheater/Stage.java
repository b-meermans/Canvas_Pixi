package AopsTheater;

import java.util.ArrayList;
import java.util.List;

public abstract class Stage extends AopsTheaterComponent {
    // TODO Add in removing objects

    private static final String DEFAULT_IMAGE = "dots.png";

    private final int width;
    private final int height;
    private AopsImage background;

    private String image;



    private transient final List<Actor> actors;
    private transient final List<Actor> addedActors;
    private transient final List<Text> texts;
    private transient final List<Sound> sounds;

    private transient final SpatialHashMap spatialHashMap;

    public Stage(int width, int height) {
        this(width, height, DEFAULT_IMAGE);
    }

    public Stage(int width, int height, String imageName) {
        this.width = width;
        this.height = height;
        this.background = new AopsImage(imageName);
        this.image = imageName;
        actors = new ArrayList<>();
        addedActors = new ArrayList<>();
        texts = new ArrayList<Text>();
        sounds = new ArrayList<Sound>();
        spatialHashMap = new SpatialHashMap(this, 20, 20);
    }

    public void act() {

    }

    public void addActor(Actor actor, double x, double y) {
        addedActors.add(actor);

        //first we set stage


        actor.setStage(this);
        //then we set the initial location
        actor.initializeLocation(x, y);
        //then when we have a valid stage and location we call addedToStage
        actor.addedToStage(this);


        //now that the actor is successfully placed in the stage we can insert it into the spatialHashMap
        spatialHashMap.insertNew(actor);
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

    public AopsImage getBackground() {
        return background;
    }

    //temporary
    public String getImage() {
        return image;
    }

    public void setImage(String imageFilename) {
        this.image = imageFilename;
    }

    public void setBackground(String background) {
        this.background = new AopsImage(background);
    }

    public void setBackground(AopsImage background) {
        this.background = background;
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
    SpatialHashMap getSpatialHashMap() {
        return spatialHashMap;
    }
    
    public<A extends Actor> List<A> getObjectsInRange(Class<A> cls, double x, double y, double radius) {
           Coordinate coordinate = new Coordinate(x,y);
           return spatialHashMap.getAllWithinRadius(cls, coordinate, radius);
    }
    public<A extends Actor> List<A> getKNearestObjectsInRadius(Class<A> cls, double x, double y, double radius, int k) {
        Coordinate coordinate = new Coordinate(x, y);
        return spatialHashMap.getKNearestWithinRadius(cls, coordinate, radius, k);
    }

    // TODO Get all Objects (Text, Actor)
}

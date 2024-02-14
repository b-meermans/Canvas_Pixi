package AopsTheater;

public class Sound extends AopsTheaterComponent {
    // TODO Is it important to find the current status of the sound from the front end? Ie: is it playing still?

    private final String fileName;
    private double volume = 1;
    private Status status = Status.STOP;

    enum Status {
        PLAY,
        PLAY_LOOP,
        PAUSE,
        STOP;

        @Override
        public String toString() {
            return name().replaceAll("_", " ");
        }
    }

    public Sound(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = Math.max(0.0, Math.min(1.0, volume));
    }

    Status getStatus() {
        return status;
    }

    public void play() {
        status = Status.PLAY;
    }

    public void playLoop() {
        status = Status.PLAY_LOOP;
    }

    public void pause() {
        status = Status.PAUSE;
    }

    public void stop() {
        status = Status.STOP;
    }
}

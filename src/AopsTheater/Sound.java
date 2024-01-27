package AopsTheater;

import java.util.UUID;

public class Sound {
    // TODO Is it important to find the current status of the sound from the front end? Ie: is it playing still?

    UUID uuid;

    private final String fileName;
    private double volume;
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
        uuid = UUID.randomUUID();
    }

    UUID getId() {
        return uuid;
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
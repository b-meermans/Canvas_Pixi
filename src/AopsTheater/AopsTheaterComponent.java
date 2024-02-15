package AopsTheater;

import java.util.UUID;

public abstract class AopsTheaterComponent {
    String uuid;

    public AopsTheaterComponent() {
        uuid = UUID.randomUUID().toString();
    }

    String getUUID() {
        return uuid.toString();
    }

    // TODO Remove this - just for testing purposes
    public void setUuid(String val) {
        uuid = val;
    }
}

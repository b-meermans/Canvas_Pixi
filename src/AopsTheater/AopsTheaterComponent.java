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
}

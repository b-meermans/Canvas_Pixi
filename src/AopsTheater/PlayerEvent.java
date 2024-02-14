package AopsTheater;

import java.util.Set;

import java.util.HashSet;

class PlayerEvent {
    private int id;
    private double mouseX;
    private double mouseY;
    private boolean leftMouseClick;
    private boolean rightMouseClick;
    private Set<String> pressedKeys;

    // Private constructor to enforce the use of the builder
    private PlayerEvent(Builder builder) {
        this.id = builder.id;
        this.mouseX = builder.mouseX;
        this.mouseY = builder.mouseY;
        this.leftMouseClick = builder.leftMouseClick;
        this.rightMouseClick = builder.rightMouseClick;
        this.pressedKeys = builder.pressedKeys;
    }

    public int getID() {
        return id;
    }
    // Getter methods
    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public boolean isLeftMouseClick() {
        return leftMouseClick;
    }

    public boolean isRightMouseClick() {
        return rightMouseClick;
    }

    public Set<String> getPressedKeys() {
        return pressedKeys;
    }

    public boolean isKeyPressed(String key) {
        return pressedKeys.contains(key);
    }

    // Builder class
    public static class Builder {
        private int id;
        private double mouseX;
        private double mouseY;
        private boolean leftMouseClick;
        private boolean rightMouseClick;
        private Set<String> pressedKeys;

        public Builder() {
            this.pressedKeys = new HashSet<>();
        }

        public Builder setID(int id) {
            this.id = id;
            return this;
        }

        public Builder mouseX(double mouseX) {
            this.mouseX = mouseX;
            return this;
        }

        public Builder mouseY(double mouseY) {
            this.mouseY = mouseY;
            return this;
        }

        public Builder leftMouseClick(boolean leftMouseClick) {
            this.leftMouseClick = leftMouseClick;
            return this;
        }

        public Builder rightMouseClick(boolean rightMouseClick) {
            this.rightMouseClick = rightMouseClick;
            return this;
        }

        public Builder pressedKeys(Set<String> pressedKeys) {
            this.pressedKeys = pressedKeys;
            return this;
        }

        public PlayerEvent build() {
            PlayerEvent playerEvent = new PlayerEvent(this);
            this.pressedKeys = new HashSet<>();

            return playerEvent;
        }
    }

    @Override
    public String toString() {
        return "PlayerEvent{" +
                "id=" + id +
                ", mouseX=" + mouseX +
                ", mouseY=" + mouseY +
                ", leftMouseClick=" + leftMouseClick +
                ", rightMouseClick=" + rightMouseClick +
                ", pressedKeys=" + pressedKeys +
                '}';
    }
}

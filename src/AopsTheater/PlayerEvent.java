package AopsTheater;

import java.util.Set;

import java.util.HashSet;

public class PlayerEvent {
    private double mouseX;
    private double mouseY;
    private boolean leftMouseClick;
    private boolean rightMouseClick;
    private Set<String> pressedKeys;

    // Private constructor to enforce the use of the builder
    private PlayerEvent(Builder builder) {
        this.mouseX = builder.mouseX;
        this.mouseY = builder.mouseY;
        this.leftMouseClick = builder.leftMouseClick;
        this.rightMouseClick = builder.rightMouseClick;
        this.pressedKeys = builder.pressedKeys;
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

    // Builder class
    public static class Builder {
        private double mouseX;
        private double mouseY;
        private boolean leftMouseClick;
        private boolean rightMouseClick;
        private Set<String> pressedKeys;

        public Builder() {
            this.pressedKeys = new HashSet<>();
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
            return new PlayerEvent(this);
        }
    }
}

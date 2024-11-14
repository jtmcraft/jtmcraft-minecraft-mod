package net.fasilsmp.mods.jtmcraft.util;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ColorChanger {
    private static final float MIN_HUE = 0.0F;
    private static final float MAX_HUE = 1.0F;

    private boolean hueIsIncreasing;
    private float currentHue;
    private final float hueChangeFactor;
    private static final float DEFAULT_HUE_CHANGE_FACTOR = 0.00009F;

    public ColorChanger(float hueChangeFactor) {
        hueIsIncreasing = true;
        currentHue = MIN_HUE;
        this.hueChangeFactor = hueChangeFactor;
    }

    public ColorChanger() {
        this(DEFAULT_HUE_CHANGE_FACTOR);
    }

    public void updateHue() {
        if (hueIsIncreasing) {
            increaseHue();
        } else {
            decreaseHue();
        }
    }

    private void increaseHue() {
        currentHue += hueChangeFactor;
        if (currentHue > MIN_HUE) {
            currentHue = MAX_HUE;
            hueIsIncreasing = false;
        }
    }

    private void decreaseHue() {
        currentHue -= hueChangeFactor;
        if (currentHue < MIN_HUE) {
            currentHue = MIN_HUE;
            hueIsIncreasing = true;
        }
    }

    public float @NotNull [] generateNormalizedColorArray() {
        Color color = Color.getHSBColor(currentHue, 1F, 1F);
        return new float[]{color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F};
    }
}

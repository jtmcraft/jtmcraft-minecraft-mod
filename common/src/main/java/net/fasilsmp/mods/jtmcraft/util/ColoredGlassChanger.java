package net.fasilsmp.mods.jtmcraft.util;

import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ColoredGlassChanger {
    private static final int DEFAULT_RATE = 40;
    private static final List<String> DEFAULT_COLORS = List.of("Red", "Orange", "Yellow", "Lime", "Light Blue", "Cyan", "Purple", "Magenta", "Pink");
    private final List<String> colors;
    private final int colorChangeRate;

    public ColoredGlassChanger(List<String> colors, int colorChangeRate) {
        this.colors = colors;
        this.colorChangeRate = colorChangeRate;
    }

    public ColoredGlassChanger(int colorChangeRate) {
        this(DEFAULT_COLORS, colorChangeRate);
    }

    public ColoredGlassChanger(List<String> colors) {
        this(colors, DEFAULT_RATE);
    }

    public ColoredGlassChanger() {
        this(DEFAULT_COLORS, DEFAULT_RATE);
    }

    public String selectColoredGlass(float tickDelta, @NotNull World world) {
        int timeStep = (int)((world.getTime() + tickDelta) * 4);
        double sinusoidalPattern = Math.abs(Math.sin(Math.toRadians((double) timeStep / colorChangeRate)));
        int colorIndex = (int) Math.round(sinusoidalPattern * (colors.size() - 1));
        String currentColor = colors.get(colorIndex).replace(" ", "_").toLowerCase();

        return String.format("%s_stained_glass", currentColor);
    }
}

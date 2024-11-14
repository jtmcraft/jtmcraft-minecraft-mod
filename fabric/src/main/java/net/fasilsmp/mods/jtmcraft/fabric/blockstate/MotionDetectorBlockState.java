package net.fasilsmp.mods.jtmcraft.fabric.blockstate;

import net.minecraft.util.StringIdentifiable;

public enum MotionDetectorBlockState implements StringIdentifiable {
    WAITING("waiting"),
    ACTIVATED("activated"),
    IDLING("idling");

    private final String name;

    MotionDetectorBlockState(String pName) {
        this.name = pName;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}

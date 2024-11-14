package net.fasilsmp.mods.jtmcraft.fabric.blockstate;

import net.minecraft.util.StringIdentifiable;

public enum LogicGateBlockState implements StringIdentifiable {
    A_B("a_b"),
    A_NOT_B("a_not_b"),
    NOT_A_B("not_a_b"),
    NOT_A_NOT_B("not_a_not_b");

    private final String name;

    LogicGateBlockState(String pName) {
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
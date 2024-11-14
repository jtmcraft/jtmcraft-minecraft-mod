package net.fasilsmp.mods.jtmcraft.fabric.mixin;

import net.minecraft.nbt.NbtCompound;

public interface EntityDataSaver {
    NbtCompound jtmcraft$getPersistentData();
}

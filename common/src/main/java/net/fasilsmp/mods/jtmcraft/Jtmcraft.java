package net.fasilsmp.mods.jtmcraft;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Jtmcraft {
    public static final String MOD_ID = "jtmcraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
    }

    public static @NotNull Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }

    public static @NotNull Identifier c(String name) {
        return new Identifier("c", name);
    }
}

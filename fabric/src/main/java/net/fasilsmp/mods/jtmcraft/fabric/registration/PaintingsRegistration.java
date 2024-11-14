package net.fasilsmp.mods.jtmcraft.fabric.registration;

import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PaintingsRegistration {
    public static final PaintingVariant JTMCRAFT_PAINTING = registerPainting("jtmcraft", new PaintingVariant(32, 32));

    private static PaintingVariant registerPainting(String name, PaintingVariant paintingVariant) {
        return Registry.register(Registries.PAINTING_VARIANT, Jtmcraft.id(name), paintingVariant);
    }

    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft paintings");
    }
}

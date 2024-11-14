package net.fasilsmp.mods.jtmcraft.fabric.registration;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.client.screen.EllipseTracerScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlersRegistration {
    public static final ScreenHandlerType<EllipseTracerScreenHandler> ELLIPSE_BLOCK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER,
                    Jtmcraft.id("ellipse_stone_screen_handler"),
                    new ExtendedScreenHandlerType<>(EllipseTracerScreenHandler::new));

    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft screen handlers");
    }
}

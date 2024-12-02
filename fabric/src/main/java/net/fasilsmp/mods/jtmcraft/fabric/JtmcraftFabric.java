package net.fasilsmp.mods.jtmcraft.fabric;

import net.fabricmc.api.ModInitializer;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.BlockEntities;
import net.fasilsmp.mods.jtmcraft.fabric.registration.BlocksRegistration;
import net.fasilsmp.mods.jtmcraft.fabric.registration.CommandsRegistration;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemGroupRegistration;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemsRegistration;
import net.fasilsmp.mods.jtmcraft.fabric.registration.PaintingsRegistration;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ScreenHandlersRegistration;
import net.fasilsmp.mods.jtmcraft.fabric.registration.SoundsRegistration;

public final class JtmcraftFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Jtmcraft.init();
        ItemGroupRegistration.register();
        ItemsRegistration.register();
        BlocksRegistration.register();
        BlockEntities.register();
        ScreenHandlersRegistration.register();
        PaintingsRegistration.register();
        CommandsRegistration.register();
        SoundsRegistration.register();
    }
}

package net.fasilsmp.mods.jtmcraft.forge;

import dev.architectury.platform.forge.EventBuses;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Jtmcraft.MOD_ID)
public final class JtmcraftBlocksForge {
    public JtmcraftBlocksForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Jtmcraft.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Jtmcraft.init();
    }
}

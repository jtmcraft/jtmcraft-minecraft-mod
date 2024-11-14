package net.fasilsmp.mods.jtmcraft.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fasilsmp.mods.jtmcraft.fabric.registration.BlocksRegistration;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemGroupRegistration;

public class JtmcraftLootTableProvider extends FabricBlockLootTableProvider {
    public JtmcraftLootTableProvider(FabricDataOutput fabricDataOutput) {
        super(fabricDataOutput);
    }

    @Override
    public void generate() {
        ItemGroupRegistration.JTMCRAFT_BLOCK_NAMES.forEach(name -> addDrop(BlocksRegistration.getFromName(name)));
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.NotNull;

public class DataGeneratorFabric implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(@NotNull FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(JtmcraftLootTableProvider::new);
        pack.addProvider(JtmcraftBlockTagProvider::new);
        pack.addProvider(JtmcraftItemTagProvider::new);
        pack.addProvider(JtmcraftModelProvider::new);
        pack.addProvider(JtmcraftRecipeProvider::new);
        pack.addProvider(JtmcraftPaintingVariantTagProvider::new);
        pack.addProvider(JtmcraftPointOfInterestTagProvider::new);
    }
}

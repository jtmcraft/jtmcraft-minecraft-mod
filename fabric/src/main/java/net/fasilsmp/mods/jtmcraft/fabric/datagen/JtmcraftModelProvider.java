package net.fasilsmp.mods.jtmcraft.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemsRegistration;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JtmcraftModelProvider extends FabricModelProvider {
    public JtmcraftModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(@NotNull ItemModelGenerator itemModelGenerator) {
        List.of(ItemsRegistration.WOODEN_MINING_TOOL, ItemsRegistration.STONE_MINING_TOOL, ItemsRegistration.IRON_MINING_TOOL, ItemsRegistration.GOLDEN_MINING_TOOL, ItemsRegistration.DIAMOND_MINING_TOOL, ItemsRegistration.NETHERITE_MINING_TOOL)
                .forEach(item -> itemModelGenerator.register(item, Models.HANDHELD));

        itemModelGenerator.register(ItemsRegistration.MUSIC_DISC_ODE_TO_JOY, Models.GENERATED);
    }
}

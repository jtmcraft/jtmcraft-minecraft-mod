package net.fasilsmp.mods.jtmcraft.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemsRegistration;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JtmcraftModelProvider extends FabricModelProvider {
    private final List<Item> miningTools = List.of(
            ItemsRegistration.WOODEN_MINING_TOOL,
            ItemsRegistration.STONE_MINING_TOOL,
            ItemsRegistration.IRON_MINING_TOOL,
            ItemsRegistration.GOLDEN_MINING_TOOL,
            ItemsRegistration.DIAMOND_MINING_TOOL,
            ItemsRegistration.NETHERITE_MINING_TOOL);

    public final List<Item> musicDiscs = List.of(
            ItemsRegistration.MUSIC_DISC_ODE_TO_JOY,
            ItemsRegistration.MUSIC_DISC_SUNSET,
            ItemsRegistration.MUSIC_DISC_BLOCK_PARTY,
            ItemsRegistration.MUSIC_DISC_GLAD_YOURE_HERE,
            ItemsRegistration.MUSIC_DISC_RUN_AND_JUMP);

    public JtmcraftModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(@NotNull ItemModelGenerator itemModelGenerator) {
        registerItems(miningTools, itemModelGenerator, Models.HANDHELD);
        registerItems(musicDiscs, itemModelGenerator, Models.GENERATED);
    }

    private void registerItems(@NotNull List<Item> items, ItemModelGenerator generator, Model model) {
        items.forEach(item -> generator.register(item, model));
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }
}

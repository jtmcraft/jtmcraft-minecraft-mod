package net.fasilsmp.mods.jtmcraft.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemsRegistration;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class JtmcraftItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public JtmcraftItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        addMusicDiscsToTag();
        addMiningToolsToTags();
    }

    private void addMusicDiscsToTag() {
        List.of(
                ItemsRegistration.MUSIC_DISC_ODE_TO_JOY,
                ItemsRegistration.MUSIC_DISC_SUNSET,
                ItemsRegistration.MUSIC_DISC_BLOCK_PARTY,
                ItemsRegistration.MUSIC_DISC_GLAD_YOURE_HERE,
                ItemsRegistration.MUSIC_DISC_RUN_AND_JUMP
        ).forEach(item -> getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(item));
    }

    private void addMiningToolsToTags() {
        List.of(
                ItemTags.AXES,
                ItemTags.HOES,
                ItemTags.PICKAXES,
                ItemTags.SHOVELS
        ).forEach(this::addMiningToolToTag);
    }

    private void addMiningToolToTag(TagKey<Item> tag) {
        getOrCreateTagBuilder(tag)
                .add(ItemsRegistration.WOODEN_MINING_TOOL)
                .add(ItemsRegistration.STONE_MINING_TOOL)
                .add(ItemsRegistration.IRON_MINING_TOOL)
                .add(ItemsRegistration.GOLDEN_MINING_TOOL)
                .add(ItemsRegistration.DIAMOND_MINING_TOOL)
                .add(ItemsRegistration.NETHERITE_MINING_TOOL);
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemsRegistration;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class JtmcraftItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public JtmcraftItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(ItemsRegistration.MUSIC_DISC_ODE_TO_JOY);

        addMiningToolToTag(ItemTags.AXES);
        addMiningToolToTag(ItemTags.HOES);
        addMiningToolToTag(ItemTags.PICKAXES);
        addMiningToolToTag(ItemTags.SHOVELS);
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

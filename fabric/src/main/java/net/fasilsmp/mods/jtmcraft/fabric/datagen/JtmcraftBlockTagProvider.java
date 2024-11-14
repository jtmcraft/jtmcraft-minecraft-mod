package net.fasilsmp.mods.jtmcraft.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fasilsmp.mods.jtmcraft.fabric.item.JtmcraftMiningTool;
import net.fasilsmp.mods.jtmcraft.fabric.registration.BlocksRegistration;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class JtmcraftBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public JtmcraftBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        List.of(BlockTags.PICKAXE_MINEABLE).forEach(blockTagKey -> {
            addBlocksToTag(List.of("redstone_vertical_emitter", "redstone_lantern", "rainbow_block", "ellipse_stone"), blockTagKey);
        });

        addMiningToolToMineableTags(List.of(
                BlockTags.AXE_MINEABLE,
                BlockTags.HOE_MINEABLE,
                BlockTags.PICKAXE_MINEABLE,
                BlockTags.SHOVEL_MINEABLE
        ));
    }

    private void addMiningToolToMineableTags(@NotNull List<TagKey<Block>> tagKeys) {
        tagKeys.forEach(tagKey -> getOrCreateTagBuilder(JtmcraftMiningTool.MINING_TOOL_BLOCKS).forceAddTag(tagKey));
    }

    private void addBlocksToTag(@NotNull List<String> blockNames, TagKey<Block> blockTagKey) {
        blockNames.forEach(name -> getOrCreateTagBuilder(blockTagKey).add(BlocksRegistration.getFromName(name)));
    }
}

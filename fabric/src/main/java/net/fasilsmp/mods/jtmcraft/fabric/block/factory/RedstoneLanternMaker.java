package net.fasilsmp.mods.jtmcraft.fabric.block.factory;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fasilsmp.mods.jtmcraft.fabric.block.RedstoneLanternBlock;
import net.minecraft.block.Block;
import org.jetbrains.annotations.NotNull;

public class RedstoneLanternMaker extends JtmcraftBlockFactory {
    @Override
    protected Block createBlock(@NotNull FabricBlockSettings settings) {
        return new RedstoneLanternBlock(settings
                .luminance(RedstoneLanternBlock::getLuminance)
        );
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.block.factory;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fasilsmp.mods.jtmcraft.fabric.block.SmallRedstoneLanternBlock;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.BaseRedstoneLanternBlockEntity;
import net.minecraft.block.Block;

public class SmallRedstoneLanternMaker extends JtmcraftBlockFactory {
    @Override
    protected Block createBlock(FabricBlockSettings settings) {
        return new SmallRedstoneLanternBlock(settings.luminance(BaseRedstoneLanternBlockEntity::getLuminance));
    }
}

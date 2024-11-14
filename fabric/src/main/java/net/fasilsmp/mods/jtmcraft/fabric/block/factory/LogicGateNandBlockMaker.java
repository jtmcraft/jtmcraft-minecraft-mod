package net.fasilsmp.mods.jtmcraft.fabric.block.factory;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fasilsmp.mods.jtmcraft.fabric.block.LogicGateNandBlock;
import net.minecraft.block.Block;

public class LogicGateNandBlockMaker extends JtmcraftBlockFactory {
    @Override
    protected Block createBlock(FabricBlockSettings settings) {
        return new LogicGateNandBlock(settings);
    }
}

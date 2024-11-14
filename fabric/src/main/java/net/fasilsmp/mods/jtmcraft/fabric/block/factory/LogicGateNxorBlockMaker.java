package net.fasilsmp.mods.jtmcraft.fabric.block.factory;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fasilsmp.mods.jtmcraft.fabric.block.LogicGateNxorBlock;
import net.minecraft.block.Block;

public class LogicGateNxorBlockMaker extends JtmcraftBlockFactory {
    @Override
    protected Block createBlock(FabricBlockSettings settings) {
        return new LogicGateNxorBlock(settings);
    }
}

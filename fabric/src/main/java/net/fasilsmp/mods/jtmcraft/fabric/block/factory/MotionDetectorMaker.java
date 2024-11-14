package net.fasilsmp.mods.jtmcraft.fabric.block.factory;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fasilsmp.mods.jtmcraft.fabric.block.MotionDetectorBlock;
import net.minecraft.block.Block;

public class MotionDetectorMaker extends JtmcraftBlockFactory {
    @Override
    protected Block createBlock(FabricBlockSettings settings) {
        return new MotionDetectorBlock(settings);
    }
}
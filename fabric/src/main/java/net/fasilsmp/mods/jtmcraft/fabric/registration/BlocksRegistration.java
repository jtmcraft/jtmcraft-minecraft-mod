package net.fasilsmp.mods.jtmcraft.fabric.registration;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.EllipseStoneBlockMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.JtmcraftBlockFactory;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.LogicGateAndBlockMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.LogicGateBlankBlockMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.LogicGateNandBlockMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.LogicGateNorBlockMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.LogicGateNxorBlockMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.LogicGateOrBlockMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.LogicGateXorBlockMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.MotionDetectorMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.RainbowBlockMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.RedstoneLanternMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.RedstoneVerticalEmitterMaker;
import net.fasilsmp.mods.jtmcraft.fabric.block.factory.SleepTimeDetectorMaker;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class BlocksRegistration {
    private record BlockInformation(String name, JtmcraftBlockFactory maker, Block settingsBlock) {}

    private static final BlockInformation[] blockData = {
            new BlockInformation("rainbow_block", new RainbowBlockMaker(), Blocks.IRON_BLOCK),
            new BlockInformation("ellipse_stone", new EllipseStoneBlockMaker(), Blocks.SMOOTH_STONE),

            new BlockInformation("motion_detector", new MotionDetectorMaker(), Blocks.REPEATER),
            new BlockInformation("redstone_vertical_emitter", new RedstoneVerticalEmitterMaker(), Blocks.REDSTONE_BLOCK),
            new BlockInformation("redstone_lantern", new RedstoneLanternMaker(), Blocks.GLASS),
            new BlockInformation("sleep_time_detector", new SleepTimeDetectorMaker(), Blocks.DAYLIGHT_DETECTOR),

            new BlockInformation("logic_gate_and", new LogicGateAndBlockMaker(), Blocks.REPEATER),
            new BlockInformation("logic_gate_or", new LogicGateOrBlockMaker(), Blocks.REPEATER),
            new BlockInformation("logic_gate_xor", new LogicGateXorBlockMaker(), Blocks.REPEATER),
            new BlockInformation("logic_gate_nand", new LogicGateNandBlockMaker(), Blocks.REPEATER),
            new BlockInformation("logic_gate_nor", new LogicGateNorBlockMaker(), Blocks.REPEATER),
            new BlockInformation("logic_gate_nxor", new LogicGateNxorBlockMaker(), Blocks.REPEATER),
            new BlockInformation("logic_gate_blank", new LogicGateBlankBlockMaker(), Blocks.REPEATER)
    };

    private static final Map<String, Block> blocks = Arrays.stream(blockData)
            .collect(Collectors.toMap(
                    blockInformation -> blockInformation.name,
                    blockInformation -> blockInformation.maker.registerBlock(blockInformation.name, FabricBlockSettings.copyOf(blockInformation.settingsBlock))));

    public static Block getFromName(String name) {
        return blocks.get(name);
    }

    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft blocks");
    }
}

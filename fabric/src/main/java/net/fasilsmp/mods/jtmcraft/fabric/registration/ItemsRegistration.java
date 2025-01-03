package net.fasilsmp.mods.jtmcraft.fabric.registration;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.item.JtmcraftMiningTool;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemsRegistration {
    public static final Item WOODEN_MINING_TOOL = registerItem("wooden_mining_tool",
            new JtmcraftMiningTool(1f, 1f, ToolMaterials.WOOD, JtmcraftMiningTool.MINING_TOOL_BLOCKS, new FabricItemSettings()));

    public static final Item STONE_MINING_TOOL = registerItem("stone_mining_tool",
            new JtmcraftMiningTool(1f, 1f, ToolMaterials.STONE, JtmcraftMiningTool.MINING_TOOL_BLOCKS, new FabricItemSettings()));

    public static final Item IRON_MINING_TOOL = registerItem("iron_mining_tool",
            new JtmcraftMiningTool(1f, 1f, ToolMaterials.IRON, JtmcraftMiningTool.MINING_TOOL_BLOCKS, new FabricItemSettings()));

    public static final Item GOLDEN_MINING_TOOL = registerItem("golden_mining_tool",
            new JtmcraftMiningTool(1f, 1f, ToolMaterials.GOLD, JtmcraftMiningTool.MINING_TOOL_BLOCKS, new FabricItemSettings()));

    public static final Item DIAMOND_MINING_TOOL = registerItem("diamond_mining_tool",
            new JtmcraftMiningTool(1f, 1f, ToolMaterials.DIAMOND, JtmcraftMiningTool.MINING_TOOL_BLOCKS, new FabricItemSettings()));

    public static final Item NETHERITE_MINING_TOOL = registerItem("netherite_mining_tool",
            new JtmcraftMiningTool(1f, 1f, ToolMaterials.NETHERITE, JtmcraftMiningTool.MINING_TOOL_BLOCKS, new FabricItemSettings()));

    public static final Item MUSIC_DISC_ODE_TO_JOY = registerItem("music_disc_ode_to_joy",
            new MusicDiscItem(15, SoundsRegistration.MUSIC_DISC_ODE_TO_JOY_SE,
                    new FabricItemSettings().maxCount(1), 30));

    public static final Item MUSIC_DISC_SUNSET = registerItem("music_disc_sunset",
            new MusicDiscItem(15, SoundsRegistration.MUSIC_DISC_SUNSET_SE,
                    new FabricItemSettings().maxCount(1), 41));

    public static final Item MUSIC_DISC_GLAD_YOURE_HERE = registerItem("music_disc_glad_youre_here",
            new MusicDiscItem(15, SoundsRegistration.MUSIC_DISC_GLAD_YOURE_HERE_SE,
                    new FabricItemSettings().maxCount(1), 38));

    public static final Item MUSIC_DISC_BLOCK_PARTY = registerItem("music_disc_block_party",
            new MusicDiscItem(15, SoundsRegistration.MUSIC_DISC_BLOCK_PARTY_SE,
                    new FabricItemSettings().maxCount(1), 37));

    public static final Item MUSIC_DISC_RUN_AND_JUMP = registerItem("music_disc_run_and_jump",
            new MusicDiscItem(15, SoundsRegistration.MUSIC_DISC_RUN_AND_JUMP_SE,
                    new FabricItemSettings().maxCount(1), 40));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Jtmcraft.id(name), item);
    }

    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft items");
    }
}

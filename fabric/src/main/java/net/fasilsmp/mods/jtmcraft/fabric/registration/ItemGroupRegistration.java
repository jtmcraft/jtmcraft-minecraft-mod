package net.fasilsmp.mods.jtmcraft.fabric.registration;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.List;

public class ItemGroupRegistration {
    public static final List<String> JTMCRAFT_BLOCK_NAMES = List.of(
            "motion_detector",
            "redstone_vertical_emitter",
            "redstone_lantern",
            "small_redstone_lantern",
            "sleep_time_detector",
            "rainbow_block",
            "ellipse_stone",
            "logic_gate_and",
            "logic_gate_nand",
            "logic_gate_or",
            "logic_gate_nor",
            "logic_gate_xor",
            "logic_gate_nxor",
            "logic_gate_blank"
    );

    private static final List<Item> JTMCRAFT_MINING_TOOLS = List.of(
            ItemsRegistration.WOODEN_MINING_TOOL,
            ItemsRegistration.STONE_MINING_TOOL,
            ItemsRegistration.IRON_MINING_TOOL,
            ItemsRegistration.GOLDEN_MINING_TOOL,
            ItemsRegistration.DIAMOND_MINING_TOOL,
            ItemsRegistration.NETHERITE_MINING_TOOL
    );

    private static final List<Item> JTMCRAFT_DISCS = List.of(
            ItemsRegistration.MUSIC_DISC_ODE_TO_JOY,
            ItemsRegistration.MUSIC_DISC_SUNSET,
            ItemsRegistration.MUSIC_DISC_BLOCK_PARTY,
            ItemsRegistration.MUSIC_DISC_GLAD_YOURE_HERE,
            ItemsRegistration.MUSIC_DISC_RUN_AND_JUMP
    );

    public static final ItemGroup JTMCRAFT_GROUP = Registry.register(Registries.ITEM_GROUP,
            Jtmcraft.id("jtmcraft_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.jtmcraft_group"))
                    .icon(() -> new ItemStack(ItemsRegistration.DIAMOND_MINING_TOOL)).entries((displayContext, entries) -> {
                        JTMCRAFT_DISCS.forEach(entries::add);
                        JTMCRAFT_MINING_TOOLS.forEach(entries::add);
                        JTMCRAFT_BLOCK_NAMES.forEach(name -> entries.add(BlocksRegistration.getFromName(name)));
                    })
                    .build());

    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft item group");
    }
}
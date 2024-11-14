package net.fasilsmp.mods.jtmcraft.fabric.block.factory;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public abstract class JtmcraftBlockFactory {
    protected abstract Block createBlock(FabricBlockSettings settings);

    public Block registerBlock(String name, FabricBlockSettings settings) {
        Block block = createBlock(settings);
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Jtmcraft.id(name), block);
    }

    private void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Jtmcraft.id(name),
                new BlockItem(block, new FabricItemSettings()));
    }
}

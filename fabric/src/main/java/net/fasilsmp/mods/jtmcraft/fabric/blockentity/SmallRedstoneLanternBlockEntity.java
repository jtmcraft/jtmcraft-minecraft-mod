package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class SmallRedstoneLanternBlockEntity extends BaseRedstoneLanternBlockEntity {
    public SmallRedstoneLanternBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.SMALL_REDSTONE_LANTERN_BE_TYPE, pos, state);
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.fasilsmp.mods.jtmcraft.fabric.block.RedstoneLanternBlock;
import net.fasilsmp.mods.jtmcraft.fabric.blockstate.BlockStateUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public class RedstoneLanternBlockEntity extends BaseRedstoneLanternBlockEntity {
    public RedstoneLanternBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.REDSTONE_LANTERN_BE_TYPE, pos, state);
    }
}

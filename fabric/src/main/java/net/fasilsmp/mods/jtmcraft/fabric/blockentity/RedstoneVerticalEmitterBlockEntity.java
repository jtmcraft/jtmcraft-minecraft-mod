package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.fasilsmp.mods.jtmcraft.fabric.block.RedstoneVerticalEmitterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class RedstoneVerticalEmitterBlockEntity extends BlockEntity {
    public RedstoneVerticalEmitterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.REDSTONE_VERTICAL_EMITTER_BE_TYPE, pos, state);
    }

    public void tick(@NotNull ServerWorld serverWorld, BlockPos blockPos, BlockState blockState) {
        if (serverWorld.isClient) {
            return;
        }

        serverWorld.setBlockState(blockPos, blockState.with(RedstoneVerticalEmitterBlock.POWERED, serverWorld.isReceivingRedstonePower(blockPos)));
    }
}

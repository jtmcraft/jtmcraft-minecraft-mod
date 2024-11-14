package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.fasilsmp.mods.jtmcraft.fabric.block.RedstoneLanternBlock;
import net.fasilsmp.mods.jtmcraft.fabric.blockstate.BlockStateUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public class RedstoneLanternBlockEntity extends BlockEntity {
    public RedstoneLanternBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.REDSTONE_LANTERN_BE_TYPE, pos, state);
    }

    public void tick(@NotNull ServerWorld serverWorld, BlockPos blockPos, BlockState blockState) {
        if (serverWorld.isClient) {
            return;
        }

        switchLanternOnOrOff(serverWorld, blockPos, blockState);
    }

    private void switchLanternOnOrOff(@NotNull ServerWorld serverWorld, BlockPos blockPos, BlockState blockState) {
        if (serverWorld.isClient) {
            return;
        }

        boolean isLanternOff = !blockState.get(RedstoneLanternBlock.LIT);

        for (int i = 1; i <= 16; i++) {
            if (hasRedstonePower(serverWorld, blockPos.up(i), Direction.DOWN) || hasRedstonePower(serverWorld, blockPos.down(i), Direction.UP)) {
                if (isLanternOff) {
                    switchLanternOn(serverWorld, blockPos, blockState);
                }
                return;
            }
        }

        if (!isLanternOff) {
            switchLanternOff(serverWorld, blockPos, blockState);
        }
    }

    private boolean hasRedstonePower(@NotNull ServerWorld serverWorld, BlockPos position, Direction direction) {
        BlockState state = serverWorld.getBlockState(position);
        int redstonePower = BlockStateUtil.getEmittedWeakRedstonePower(serverWorld, state, position, direction);
        return redstonePower > 0;
    }

    private void switchLanternOn(@NotNull ServerWorld serverWorld, BlockPos blockPos, BlockState blockState) {
        if (serverWorld.isClient) {
            return;
        }

        serverWorld.setBlockState(blockPos, blockState.with(RedstoneLanternBlock.LIT, true));
    }

    private void switchLanternOff(@NotNull ServerWorld serverWorld, BlockPos blockPos, BlockState blockState) {
        if (serverWorld.isClient) {
            return;
        }

        serverWorld.setBlockState(blockPos, blockState.with(RedstoneLanternBlock.LIT, false));
    }
}

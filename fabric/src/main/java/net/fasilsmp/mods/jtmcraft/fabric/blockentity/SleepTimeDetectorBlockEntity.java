package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.fasilsmp.mods.jtmcraft.fabric.block.SleepTimeDetectorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class SleepTimeDetectorBlockEntity extends BlockEntity {
    private static final long SLEEP_TIME_START = 12542;

    public SleepTimeDetectorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.SLEEP_TIME_DETECTOR_BE_TYPE, blockPos, blockState);
    }

    public void tick(@NotNull ServerWorld serverWorld, BlockPos blockPos, BlockState blockState) {
        if (isClientSide(serverWorld)) {
            return;
        }

        detectSleepTime(serverWorld, blockPos, blockState);
    }

    private void detectSleepTime(@NotNull ServerWorld serverWorld, BlockPos blockPos, BlockState blockState) {
        if (isClientSide(serverWorld) || !bedWorks(serverWorld)) {
            return;
        }

        boolean canSleep = isSleepTime(serverWorld);
        boolean showsSleepTime = blockState.get(SleepTimeDetectorBlock.SLEEP_TIME);
        updateSleepTimeState(serverWorld, blockPos, blockState, canSleep, showsSleepTime);
    }

    private void updateSleepTimeState(ServerWorld serverWorld, BlockPos blockPos, BlockState blockState, boolean canSleep, boolean showsSleepTime) {
        if (canSleep != showsSleepTime) {
            blockState = blockState.with(SleepTimeDetectorBlock.SLEEP_TIME, canSleep);
            serverWorld.setBlockState(blockPos, blockState);
        }
    }

    private boolean isClientSide(@NotNull ServerWorld serverWorld) {
        return serverWorld.isClient;
    }

    private boolean bedWorks(@NotNull ServerWorld serverWorld) {
        return serverWorld.getDimension().bedWorks();
    }

    private boolean isSleepTime(@NotNull ServerWorld serverWorld) {
        return serverWorld.getTimeOfDay() % 24000 >= SLEEP_TIME_START;
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.blockstate;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;

public class BlockStateUtil {
    public static int getEmittedWeakRedstonePower(BlockView blockView, @NotNull BlockState blockState, BlockPos blockPos, Direction direction) {
        if (blockState.emitsRedstonePower()) {
            return blockState.getWeakRedstonePower(blockView, blockPos, direction);
        }

        return 0;
    }
}

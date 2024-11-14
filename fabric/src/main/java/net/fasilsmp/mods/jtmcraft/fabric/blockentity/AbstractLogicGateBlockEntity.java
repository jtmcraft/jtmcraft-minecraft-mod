package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLogicGateBlockEntity extends BlockEntity {
    public AbstractLogicGateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tick(@NotNull World world, BlockPos currentBlockPos, BlockState currentBlockState) {}

    public static int getTextLineHeight() {
        return 10;
    }

    public static int getMaxTextWidth() {
        return 90;
    }

    public static float getTextScale() {
        return 0.6666667F;
    }

    public static int getNumberOfLines() {
        return 4;
    }

    public static @NotNull Vec3d getTextOffset() {
        return new Vec3d(0.0, 0.533333, 0.0);
    }
}

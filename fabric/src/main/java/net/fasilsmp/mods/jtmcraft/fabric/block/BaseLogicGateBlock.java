package net.fasilsmp.mods.jtmcraft.fabric.block;

import net.fasilsmp.mods.jtmcraft.fabric.blockstate.LogicGateBlockState;
import net.fasilsmp.mods.jtmcraft.logicfunctions.LogicGateBiFunction;
import net.fasilsmp.mods.jtmcraft.util.JtmcraftMath;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseLogicGateBlock extends AbstractRedstoneGateBlock {
    public static final EnumProperty<LogicGateBlockState> LOGIC_GATE_STATE = EnumProperty.of("logic_gate_state", LogicGateBlockState.class);
    private final LogicGateBiFunction logicGateBiFunction;
    public record RedstonePower(int clockwisePower, int counterClockwisePower) {}

    protected abstract LogicGateBiFunction configureLogicGateFunction();

    public BaseLogicGateBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(POWERED, false)
                .with(FACING, Direction.NORTH)
                .with(LOGIC_GATE_STATE, LogicGateBlockState.NOT_A_NOT_B));
        this.logicGateBiFunction = configureLogicGateFunction();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POWERED);
        builder.add(FACING);
        builder.add(LOGIC_GATE_STATE);
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 2;
    }

    @Override
    public void scheduledTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        evaluateLogicGate(blockPos, blockState, serverWorld);
    }

    @Override
    public void onPlaced(@NotNull World world, BlockPos blockPos, BlockState blockState, LivingEntity placer, ItemStack itemStack) {
        world.scheduleBlockTick(blockPos, this, 1);
    }

    @Override
    public void neighborUpdate(BlockState blockState, World world, BlockPos blockPos, Block sourceBlock, BlockPos sourceBlockPos, boolean notify) {
        evaluateLogicGate(blockPos, blockState, world);
    }

    private @NotNull RedstonePower getPowerOnSides(@NotNull BlockState blockState, BlockPos blockPos, World world) {
        Direction mainDirection = blockState.get(BaseLogicGateBlock.FACING);
        int redstonePowerClockwise = getDirectedRedstonePower(mainDirection, blockPos, world, true);
        int redstonePowerCounterClockwise = getDirectedRedstonePower(mainDirection, blockPos, world, false);
        return new RedstonePower(redstonePowerClockwise, redstonePowerCounterClockwise);
    }

    private int getDirectedRedstonePower(Direction mainDirection, BlockPos blockPos, World world, boolean isClockwise) {
        BlockPos directedBlockPos = getRelatedBlockPos(mainDirection, blockPos, isClockwise);
        return getReceivedRedstonePower(world, directedBlockPos, mainDirection, isClockwise);
    }

    private boolean applyLogicGateFunction(BlockState blockState, BlockPos blockPos, World world) {
        RedstonePower redstonePower = getPowerOnSides(blockState, blockPos, world);
        return logicGateBiFunction.apply(redstonePower.clockwisePower, redstonePower.counterClockwisePower);
    }

    private void updateBlockStateIfNeeded(World world, BlockPos blockPos, @NotNull BlockState blockState, LogicGateBlockState newLogicGateBlockState, boolean gateOpened) {
        BlockState newBlockState = blockState;
        boolean stateChanged = false;

        if (blockState.get(BaseLogicGateBlock.LOGIC_GATE_STATE) != newLogicGateBlockState) {
            newBlockState = newBlockState.with(BaseLogicGateBlock.LOGIC_GATE_STATE, newLogicGateBlockState);
            stateChanged = true;
        }

        boolean powered = blockState.get(BaseLogicGateBlock.POWERED);
        if (!powered && gateOpened) {
            newBlockState = newBlockState.with(BaseLogicGateBlock.POWERED, true);
            stateChanged = true;
        } else if (powered && !gateOpened) {
            newBlockState = newBlockState.with(BaseLogicGateBlock.POWERED, false);
            stateChanged = true;
        }

        if (stateChanged) {
            world.setBlockState(blockPos, newBlockState, 2);
        }
    }

    private void evaluateLogicGate(BlockPos blockPos, BlockState blockState, @NotNull World world) {
        RedstonePower redstonePower = getPowerOnSides(blockState, blockPos, world);
        boolean gateOpened = applyLogicGateFunction(blockState, blockPos, world);
        LogicGateBlockState newLogicGateBlockState = buildNewLogicGateBlockState(redstonePower);

        updateBlockStateIfNeeded(world, blockPos, blockState, newLogicGateBlockState, gateOpened);
    }

    @Nullable
    protected <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createBlockEntityTicker(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    private BlockPos getRelatedBlockPos(Direction mainDirection, BlockPos currentBlockPos, boolean isClockwise) {
        return isClockwise ? JtmcraftMath.getBlockPosClockwise(currentBlockPos, mainDirection) : JtmcraftMath.getBlockPosCounterClockwise(currentBlockPos, mainDirection);
    }

    private int getReceivedRedstonePower(@NotNull World serverWorld, BlockPos relatedBlockPos, Direction mainDirection, boolean isClockwise) {
        Direction relatedDirection = isClockwise ? JtmcraftMath.getDirectionClockwise(mainDirection) : JtmcraftMath.getDirectionCounterClockwise(mainDirection);
        return serverWorld.getEmittedRedstonePower(relatedBlockPos, relatedDirection);
    }

    private LogicGateBlockState buildNewLogicGateBlockState(RedstonePower redstonePower) {
        String powerState = mapPowerToState(redstonePower);
        return LogicGateBlockState.valueOf(powerState);
    }

    private @NotNull String mapPowerToState(@NotNull RedstonePower power) {
        String aState = power.clockwisePower > 0 ? "A" : "NOT_A";
        String bState = power.counterClockwisePower > 0 ? "B" : "NOT_B";
        return aState + "_" + bState;
    }
}

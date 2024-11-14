package net.fasilsmp.mods.jtmcraft.fabric.block;

import net.fasilsmp.mods.jtmcraft.fabric.blockentity.BlockEntities;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.MotionDetectorBlockEntity;
import net.fasilsmp.mods.jtmcraft.fabric.blockstate.MotionDetectorBlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.Vibrations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MotionDetectorBlock extends AbstractRedstoneGateBlock implements BlockEntityProvider {
    public static final EnumProperty<MotionDetectorBlockState> MD_STATE = EnumProperty.of("motion_detector_state", MotionDetectorBlockState.class);
    public static int STAY_ON_FOR = 100;

    public MotionDetectorBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(POWERED, false)
                .with(MD_STATE, MotionDetectorBlockState.WAITING));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(MD_STATE);
        builder.add(FACING);
        builder.add(POWERED);
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 2;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MotionDetectorBlockEntity(blockPos, blockState);
    }

    public @NotNull MotionDetectorBlockState getMotionDetectorState(@NotNull BlockState blockState) {
        return blockState.get(MD_STATE);
    }

    public static boolean canActivate(@NotNull BlockState blockstate) {
        return true;
    }

    public void activate(@NotNull ServerWorld serverLevel, BlockPos blockPos, @NotNull BlockState oldBlockState) {
        BlockState newBlockState = oldBlockState
                .with(POWERED, true)
                .with(MD_STATE, MotionDetectorBlockState.ACTIVATED);
        serverLevel.updateListeners(blockPos, oldBlockState, newBlockState, 3);
        serverLevel.scheduleBlockTick(blockPos, newBlockState.getBlock(), STAY_ON_FOR / 2);
        updateNeighbors(serverLevel, blockPos, newBlockState);
    }

    public void deactivate(@NotNull ServerWorld level, BlockPos blockPos, @NotNull BlockState oldBlockState) {
        BlockState newBlockState = oldBlockState.with(MD_STATE, MotionDetectorBlockState.IDLING);
        level.updateListeners(blockPos, oldBlockState, newBlockState, 3);
        level.scheduleBlockTick(blockPos, newBlockState.getBlock(), STAY_ON_FOR / 2);
        updateNeighbors(level, blockPos, newBlockState);
    }

    private void updateNeighbors(@NotNull World level, BlockPos blockPos, @NotNull BlockState blockState) {
        level.setBlockState(blockPos, blockState);
        Block block = blockState.getBlock();
        level.updateNeighbors(blockPos, block);
        level.updateNeighbors(blockPos.down(), block);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);

        if (blockState != null) {
            blockState = blockState.with(MD_STATE, MotionDetectorBlockState.WAITING);
        }

        return blockState;
    }

    @Override
    public void scheduledTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        if (getMotionDetectorState(blockState) != MotionDetectorBlockState.ACTIVATED) {
            if (getMotionDetectorState(blockState) == MotionDetectorBlockState.IDLING) {
                BlockState newBlockState = blockState.with(MD_STATE, MotionDetectorBlockState.WAITING).with(POWERED, false);
                updateNeighbors(serverWorld, blockPos, newBlockState);
            }
        } else {
            deactivate(serverWorld, blockPos, blockState);
        }
    }

    @Nullable
    protected <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createBlockEntityTicker(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClient ? createBlockEntityTicker(blockEntityType, BlockEntities.MOTION_DETECTOR_BE_TYPE, (pLevel, pBlockPos, pBlockState, blockEntity) -> {
            Vibrations.Ticker.tick(pLevel, blockEntity.getVibrationListenerData(), blockEntity.getVibrationCallback());
        }) : null;
    }
}

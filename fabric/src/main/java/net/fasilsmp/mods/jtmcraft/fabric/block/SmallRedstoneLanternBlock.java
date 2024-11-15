package net.fasilsmp.mods.jtmcraft.fabric.block;

import net.fasilsmp.mods.jtmcraft.fabric.blockentity.BaseRedstoneLanternBlockEntity;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.BlockEntities;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.SmallRedstoneLanternBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SmallRedstoneLanternBlock extends Block implements Waterloggable, BlockEntityProvider {
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty HANGING = Properties.HANGING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final VoxelShape STANDING_SHAPE = VoxelShapes.union(Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 7.0, 11.0), Block.createCuboidShape(6.0, 7.0, 6.0, 10.0, 9.0, 10.0));
    public static final VoxelShape HANGING_SHAPE = VoxelShapes.union(Block.createCuboidShape(5.0, 1.0, 5.0, 11.0, 8.0, 11.0), Block.createCuboidShape(6.0, 8.0, 6.0, 10.0, 10.0, 10.0));

    public SmallRedstoneLanternBlock(Settings settings) {
        super(settings.luminance(BaseRedstoneLanternBlockEntity::getLuminance));
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(HANGING, false)
                .with(WATERLOGGED, false)
                .with(LIT, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HANGING, WATERLOGGED, LIT);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView world, BlockPos blockPos, ShapeContext shapeContext) {
        return blockState.get(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        FluidState fluidState = itemPlacementContext.getWorld().getFluidState(itemPlacementContext.getBlockPos());
        Direction[] placementDirections = itemPlacementContext.getPlacementDirections();

        for (Direction direction : placementDirections) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.getDefaultState().with(HANGING, direction == Direction.UP);
                if (blockState.canPlaceAt(itemPlacementContext.getWorld(), itemPlacementContext.getBlockPos())) {
                    return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    private static Direction attachedDirection(BlockState blockState) {
        return blockState.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public boolean canPlaceAt(BlockState blockState, WorldView world, BlockPos blockPos) {
        Direction direction = attachedDirection(blockState).getOpposite();
        return Block.sideCoversSmallSquare(world, blockPos.offset(direction), direction.getOpposite());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState neighborBlockState, WorldAccess world, BlockPos blockPos, BlockPos neighborBlockPos) {
        if (blockState.get(WATERLOGGED)) {
            world.scheduleFluidTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return attachedDirection(blockState).getOpposite() == direction && !blockState.canPlaceAt(world, blockPos) ? Blocks.AIR.getDefaultState() : blockState;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public boolean canPathfindThrough(BlockState blockState, BlockView world, BlockPos blockPos, NavigationType navigationType) {
        return false;
    }

    @Nullable
    protected <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createBlockEntityTicker(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return !world.isClient ? createBlockEntityTicker(blockEntityType, BlockEntities.SMALL_REDSTONE_LANTERN_BE_TYPE, (pLevel, pBlockPos, pBlockState, blockEntity) -> {
            blockEntity.tick((ServerWorld) pLevel, pBlockPos, pBlockState);
        }) : null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SmallRedstoneLanternBlockEntity(blockPos, blockState);
    }
}

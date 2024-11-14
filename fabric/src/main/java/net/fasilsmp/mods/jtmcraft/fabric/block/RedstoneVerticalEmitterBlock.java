package net.fasilsmp.mods.jtmcraft.fabric.block;

import net.fasilsmp.mods.jtmcraft.fabric.blockentity.BlockEntities;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.RedstoneVerticalEmitterBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedstoneVerticalEmitterBlock extends Block implements BlockEntityProvider {
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public RedstoneVerticalEmitterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POWERED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakRedstonePower(@NotNull BlockState blockState, @NotNull BlockView blockView, @NotNull BlockPos blockPos, Direction direction) {
        return (direction == Direction.DOWN || direction == Direction.UP) && blockState.get(POWERED) ? 15 : 0;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RedstoneVerticalEmitterBlockEntity(blockPos, blockState);
    }

    @Nullable
    protected <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createBlockEntityTicker(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return !world.isClient ? createBlockEntityTicker(blockEntityType, BlockEntities.REDSTONE_VERTICAL_EMITTER_BE_TYPE, (pLevel, pBlockPos, pBlockState, blockEntity) -> {
            blockEntity.tick((ServerWorld) pLevel, pBlockPos, pBlockState);
        }) : null;
    }
}
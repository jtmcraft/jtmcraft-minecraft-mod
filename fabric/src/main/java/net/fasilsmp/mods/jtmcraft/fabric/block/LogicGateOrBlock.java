package net.fasilsmp.mods.jtmcraft.fabric.block;

import net.fasilsmp.mods.jtmcraft.fabric.blockentity.BlockEntities;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.LogicGateOrBlockEntity;
import net.fasilsmp.mods.jtmcraft.logicfunctions.LogicGateBiFunction;
import net.fasilsmp.mods.jtmcraft.logicfunctions.OrBiFunction;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LogicGateOrBlock extends BaseLogicGateBlock implements BlockEntityProvider {
    public LogicGateOrBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected LogicGateBiFunction configureLogicGateFunction() {
        return new OrBiFunction();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LogicGateOrBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return !world.isClient ? createBlockEntityTicker(blockEntityType, BlockEntities.LOGIC_GATE_OR_BE_TYPE,
                (pLevel, pBlockPos, pBlockState, blockEntity) -> blockEntity.tick(pLevel, pBlockPos, pBlockState)) : null;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("block.jtmcraft.logic_gate_or.tooltip"));
    }
}

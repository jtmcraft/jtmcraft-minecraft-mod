package net.fasilsmp.mods.jtmcraft.fabric.block;

import net.fasilsmp.mods.jtmcraft.fabric.blockentity.BlockEntities;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.LogicGateXorBlockEntity;
import net.fasilsmp.mods.jtmcraft.logicfunctions.LogicGateBiFunction;
import net.fasilsmp.mods.jtmcraft.logicfunctions.XorBiFunction;
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

public class LogicGateXorBlock extends BaseLogicGateBlock implements BlockEntityProvider {
    public LogicGateXorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected LogicGateBiFunction configureLogicGateFunction() {
        return new XorBiFunction();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LogicGateXorBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return !world.isClient ? createBlockEntityTicker(blockEntityType, BlockEntities.LOGIC_GATE_XOR_BE_TYPE,
                (pLevel, pBlockPos, pBlockState, blockEntity) -> blockEntity.tick(pLevel, pBlockPos, pBlockState)) : null;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("block.jtmcraft.logic_gate_xor.tooltip"));
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LogicGateNandBlockEntity extends AbstractLogicGateBlockEntity {
    public LogicGateNandBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LOGIC_GATE_NAND_BE_TYPE, pos, state);
    }
}

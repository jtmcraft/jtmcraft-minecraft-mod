package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LogicGateAndBlockEntity extends AbstractLogicGateBlockEntity {
    public LogicGateAndBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LOGIC_GATE_AND_BE_TYPE, pos, state);
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LogicGateOrBlockEntity extends AbstractLogicGateBlockEntity {
    public LogicGateOrBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LOGIC_GATE_OR_BE_TYPE, pos, state);
    }
}

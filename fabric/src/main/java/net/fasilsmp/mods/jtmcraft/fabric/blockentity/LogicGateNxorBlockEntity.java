package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LogicGateNxorBlockEntity extends AbstractLogicGateBlockEntity {
    public LogicGateNxorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LOGIC_GATE_NXOR_BE_TYPE, pos, state);
    }
}

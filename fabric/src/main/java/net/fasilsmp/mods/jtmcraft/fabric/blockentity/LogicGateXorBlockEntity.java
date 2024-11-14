package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LogicGateXorBlockEntity extends AbstractLogicGateBlockEntity {
    public LogicGateXorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LOGIC_GATE_XOR_BE_TYPE, pos, state);
    }
}

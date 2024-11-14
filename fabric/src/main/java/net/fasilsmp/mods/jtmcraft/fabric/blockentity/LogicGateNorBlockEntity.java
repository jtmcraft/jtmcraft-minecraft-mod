package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LogicGateNorBlockEntity extends AbstractLogicGateBlockEntity {
    public LogicGateNorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LOGIC_GATE_NOR_BE_TYPE, pos, state);
    }
}

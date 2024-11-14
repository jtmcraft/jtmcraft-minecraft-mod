package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class RainbowBlockEntity extends BlockEntity {
    List<BeaconBlockEntity.BeamSegment> beamSegments = Lists.newArrayList();

    public RainbowBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.RAINBOW_BLOCK_BE_TYPE, pos, state);
    }

    public List<BeaconBlockEntity.BeamSegment> getBeamSegments() {
        return beamSegments;
    }

    public ItemStack getRenderStack() {
        return new ItemStack(Items.GLASS);
    }
}

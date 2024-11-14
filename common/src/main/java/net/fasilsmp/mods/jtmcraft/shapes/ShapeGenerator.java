package net.fasilsmp.mods.jtmcraft.shapes;

import net.minecraft.util.math.BlockPos;

import java.util.Set;

public interface ShapeGenerator {
    void generatePlotPoints();
    Set<BlockPos> getPlotPoints();
    Set<BlockPos> getRelativePlotPoints();
}

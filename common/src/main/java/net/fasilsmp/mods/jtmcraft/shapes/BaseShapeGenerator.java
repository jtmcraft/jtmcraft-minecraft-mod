package net.fasilsmp.mods.jtmcraft.shapes;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseShapeGenerator implements ShapeGenerator {
    protected BlockPos origin;
    protected final Set<BlockPos> plotPoints;

    public static void plotSymmetricPoints(@NotNull Set<BlockPos> plotPoints, int originX, int originY, int originZ, int x, int y) {
        plotPoints.add(new BlockPos(originX + x, originY, originZ + y));
        plotPoints.add(new BlockPos(originX - x, originY, originZ + y));
        plotPoints.add(new BlockPos(originX + x, originY, originZ - y));
        plotPoints.add(new BlockPos(originX - x, originY, originZ - y));
    }

    public BaseShapeGenerator(BlockPos origin) {
        this.origin = origin;
        this.plotPoints = new LinkedHashSet<>();
    }

    @Override
    public void generatePlotPoints() {
        plotPoints.clear();
    }

    @Override
    public Set<BlockPos> getPlotPoints() {
        return Collections.unmodifiableSet(plotPoints);
    }

    @Override
    public Set<BlockPos> getRelativePlotPoints() {
        return getPlotPoints().stream()
                .map(origin::subtract)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }
}

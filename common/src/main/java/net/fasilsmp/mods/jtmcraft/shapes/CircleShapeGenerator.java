package net.fasilsmp.mods.jtmcraft.shapes;

import net.minecraft.util.math.BlockPos;

public class CircleShapeGenerator extends BaseShapeGenerator {
    private final int radius;

    public CircleShapeGenerator(BlockPos origin, int radius) {
        super(origin);
        this.radius = radius;
    }

    @Override
    public void generatePlotPoints() {
        super.generatePlotPoints();
        generateCircle(origin.getX(), origin.getY(), origin.getZ(), radius);
    }

    private void generateCircle(int originX, int originY, int originZ, int r) {
        int x = 0;
        int y = r;
        int d = 3 - 2 * r;

        plotCirclePoints(originX, originY, originZ, x, y);

        while (y >= x) {
            x++;

            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }

            plotCirclePoints(originX, originY, originZ, x, y);
        }
    }

    private void plotCirclePoints(int originX, int originY, int originZ, int x, int y) {
        BaseShapeGenerator.plotSymmetricPoints(plotPoints, originX, originY, originZ, y, x);
        BaseShapeGenerator.plotSymmetricPoints(plotPoints, originX, originY, originZ, x, y);
    }
}

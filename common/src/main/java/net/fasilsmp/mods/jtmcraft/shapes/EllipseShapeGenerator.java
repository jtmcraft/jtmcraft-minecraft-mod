package net.fasilsmp.mods.jtmcraft.shapes;

import net.minecraft.util.math.BlockPos;

public class EllipseShapeGenerator extends BaseShapeGenerator {
    private final int semiMajorAxis;
    private final int semiMinorAxis;

    public EllipseShapeGenerator(BlockPos origin, int semiMajorAxis, int semiMinorAxis) {
        super(origin);
        this.semiMajorAxis = semiMajorAxis;
        this.semiMinorAxis = semiMinorAxis;
    }

    @Override
    public void generatePlotPoints() {
        super.generatePlotPoints();
        generateEllipse(origin.getX(), origin.getY(), origin.getZ(), semiMajorAxis, semiMinorAxis);
    }

    private void generateEllipse(int centerX, int centerY, int centerZ, int rx, int ry) {
        int x = 0;
        int y = ry;
        int rxSqr = rx * rx;
        int rySqr = ry * ry;
        int twoRxSqr = 2 * rxSqr;
        int twoRySqr = 2 * rySqr;

        int p = (int) (rySqr - (rxSqr * ry) + (0.25 * rxSqr));
        int px = 0;
        int py = twoRxSqr * y;

        // Endpoints
        BaseShapeGenerator.plotSymmetricPoints(plotPoints, centerX, centerY, centerZ, 0, ry);
        BaseShapeGenerator.plotSymmetricPoints(plotPoints, centerX, centerY, centerZ, rx, 0);

        // Region 1: Slope of ellipse is less than or equal to 1
        while (px < py) {
            x++;
            px += twoRySqr;
            if (p < 0) {
                p += rySqr + px;
            } else {
                y--;
                py -= twoRxSqr;
                p += rySqr + px - py;
            }
            BaseShapeGenerator.plotSymmetricPoints(plotPoints, centerX, centerY, centerZ, x, y);
        }

        // Region 2: Slope of ellipse is greater than 1
        p = (int) (rySqr * (x + 0.5) * (x + 0.5) + rxSqr * (y - 1) * (y - 1) - rxSqr * rySqr);
        while (y > 0) {
            y--;
            py -= twoRxSqr;
            if (p > 0) {
                p += rxSqr - py;
            } else {
                x++;
                px += twoRySqr;
                p += rxSqr - py + px;
            }
            BaseShapeGenerator.plotSymmetricPoints(plotPoints, centerX, centerY, centerZ, x, y);
        }
    }
}

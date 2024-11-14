package net.fasilsmp.mods.jtmcraft.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class JtmcraftMath {
    private static final IllegalStateException INVALID_DIRECTION_EXCEPTION =
            new IllegalStateException("Invalid block direction");

    public static Direction getDirectionClockwise(Direction facing) {
        if (facing == null) {
            throw INVALID_DIRECTION_EXCEPTION;
        }

        return switch (facing) {
            case EAST -> Direction.SOUTH;
            case SOUTH -> Direction.WEST;
            case WEST -> Direction.NORTH;
            case NORTH -> Direction.EAST;
            default -> throw INVALID_DIRECTION_EXCEPTION;
        };
    }

    public static Direction getDirectionCounterClockwise(Direction facing) {
        if (facing == null) {
            throw INVALID_DIRECTION_EXCEPTION;
        }

        return switch (facing) {
            case EAST -> Direction.NORTH;
            case SOUTH -> Direction.EAST;
            case WEST -> Direction.SOUTH;
            case NORTH -> Direction.WEST;
            default -> throw INVALID_DIRECTION_EXCEPTION;
        };
    }

    public static BlockPos getBlockPosClockwise(BlockPos blockPos, Direction facing) {
        if (facing == null) {
            throw INVALID_DIRECTION_EXCEPTION;
        }

        return switch (facing) {
            case EAST -> blockPos.south();
            case SOUTH -> blockPos.west();
            case WEST -> blockPos.north();
            case NORTH -> blockPos.east();
            default -> throw INVALID_DIRECTION_EXCEPTION;
        };
    }

    public static BlockPos getBlockPosCounterClockwise(BlockPos blockPos, Direction facing) {
        if (facing == null) {
            throw INVALID_DIRECTION_EXCEPTION;
        }

        return switch (facing) {
            case EAST -> blockPos.north();
            case SOUTH -> blockPos.east();
            case WEST -> blockPos.south();
            case NORTH -> blockPos.west();
            default -> throw INVALID_DIRECTION_EXCEPTION;
        };
    }
}

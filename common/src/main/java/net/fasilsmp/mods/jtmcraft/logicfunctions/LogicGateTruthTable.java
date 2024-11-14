package net.fasilsmp.mods.jtmcraft.logicfunctions;

import org.jetbrains.annotations.NotNull;

public final class LogicGateTruthTable {
    private LogicGateTruthTable() {}

    private static @NotNull String createRow(int a, int b, boolean result) {
        return a + "\t" + b + "\t" + (result ? 1 : 0) + "\n";
    }

    public static @NotNull String format(LogicGateBiFunction biFunction) {
        StringBuilder truthTable = new StringBuilder();

        for (int a = 0; a <= 1; a++) {
            for (int b = 0; b <= 1; b++) {
                truthTable.append(createRow(a, b, biFunction.apply(a, b)));
            }
        }

        return truthTable.toString();
    }

    public static @NotNull String forAnd() {
        return format(new AndBiFunction());
    }

    public static @NotNull String forNand() {
        return format(new NandBiFunction());
    }

    public static @NotNull String forOr() {
        return format(new OrBiFunction());
    }

    public static @NotNull String forNor() {
        return format(new NorBiFunction());
    }

    public static @NotNull String forXor() {
        return format(new XorBiFunction());
    }

    public static @NotNull String forNxor() {
        return format(new NxorBiFunction());
    }
}

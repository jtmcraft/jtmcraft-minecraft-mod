package net.fasilsmp.mods.jtmcraft.logicfunctions;

public class OrBiFunction implements LogicGateBiFunction {
    @Override
    public Boolean apply(Integer a, Integer b) {
        return (a > 0) || (b > 0);
    }
}

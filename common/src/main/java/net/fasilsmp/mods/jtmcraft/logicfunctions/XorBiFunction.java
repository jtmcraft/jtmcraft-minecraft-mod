package net.fasilsmp.mods.jtmcraft.logicfunctions;

public class XorBiFunction implements LogicGateBiFunction {
    @Override
    public Boolean apply(Integer a, Integer b) {
        return ((a > 0) || (b > 0)) && !((a > 0) && (b > 0));
    }
}

package net.fasilsmp.mods.jtmcraft.logicfunctions;

public class NxorBiFunction implements LogicGateBiFunction {
    XorBiFunction xorGate = new XorBiFunction();

    @Override
    public Boolean apply(Integer a, Integer b) {
        return !xorGate.apply(a, b);
    }
}

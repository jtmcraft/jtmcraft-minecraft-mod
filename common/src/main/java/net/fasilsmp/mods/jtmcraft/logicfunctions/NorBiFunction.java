package net.fasilsmp.mods.jtmcraft.logicfunctions;

public class NorBiFunction implements LogicGateBiFunction {
    OrBiFunction orGate = new OrBiFunction();

    @Override
    public Boolean apply(Integer a, Integer b) {
        return !orGate.apply(a, b);
    }
}

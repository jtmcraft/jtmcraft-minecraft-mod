package net.fasilsmp.mods.jtmcraft.logicfunctions;

public class NandBiFunction implements LogicGateBiFunction {
    AndBiFunction andGate = new AndBiFunction();

    @Override
    public Boolean apply(Integer a, Integer b) {
        return !andGate.apply(a, b);
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.registration.BlocksRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class BlockEntities {
    public static final BlockEntityType<LogicGateAndBlockEntity> LOGIC_GATE_AND_BE_TYPE =
            registerBlockEntityType("logic_gate_and", LogicGateAndBlockEntity::new);
    public static final BlockEntityType<LogicGateNandBlockEntity> LOGIC_GATE_NAND_BE_TYPE =
            registerBlockEntityType("logic_gate_nand", LogicGateNandBlockEntity::new);
    public static final BlockEntityType<LogicGateOrBlockEntity> LOGIC_GATE_OR_BE_TYPE =
            registerBlockEntityType("logic_gate_or", LogicGateOrBlockEntity::new);
    public static final BlockEntityType<LogicGateNorBlockEntity> LOGIC_GATE_NOR_BE_TYPE =
            registerBlockEntityType("logic_gate_nor", LogicGateNorBlockEntity::new);
    public static final BlockEntityType<LogicGateXorBlockEntity> LOGIC_GATE_XOR_BE_TYPE =
            registerBlockEntityType("logic_gate_xor", LogicGateXorBlockEntity::new);
    public static final BlockEntityType<LogicGateNxorBlockEntity> LOGIC_GATE_NXOR_BE_TYPE =
            registerBlockEntityType("logic_gate_nxor", LogicGateNxorBlockEntity::new);
    public static final BlockEntityType<RainbowBlockEntity> RAINBOW_BLOCK_BE_TYPE =
            registerBlockEntityType("rainbow_block", RainbowBlockEntity::new);
    public static final BlockEntityType<MotionDetectorBlockEntity> MOTION_DETECTOR_BE_TYPE =
            registerBlockEntityType("motion_detector", MotionDetectorBlockEntity::new);
    public static final BlockEntityType<RedstoneVerticalEmitterBlockEntity> REDSTONE_VERTICAL_EMITTER_BE_TYPE =
            registerBlockEntityType("redstone_vertical_emitter", RedstoneVerticalEmitterBlockEntity::new);
    public static final BlockEntityType<RedstoneLanternBlockEntity> REDSTONE_LANTERN_BE_TYPE =
            registerBlockEntityType("redstone_lantern", RedstoneLanternBlockEntity::new);

    public static final BlockEntityType<SmallRedstoneLanternBlockEntity> SMALL_REDSTONE_LANTERN_BE_TYPE =
            registerBlockEntityType("small_redstone_lantern", SmallRedstoneLanternBlockEntity::new);

    public static final BlockEntityType<SleepTimeDetectorBlockEntity> SLEEP_TIME_DETECTOR_BE_TYPE =
            registerBlockEntityType("sleep_time_detector", SleepTimeDetectorBlockEntity::new);
    public static final BlockEntityType<EllipseTracerBlockEntity> ELLIPSE_STONE_BE_TYPE =
            registerBlockEntityType("ellipse_stone", EllipseTracerBlockEntity::new);

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String id, @NotNull BiFunction<BlockPos, BlockState, T> entityConstructor) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Jtmcraft.id(id),
                FabricBlockEntityTypeBuilder.create(entityConstructor::apply,
                                BlocksRegistration.getFromName(id))
                        .build(null));
    }

    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft block entities");
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fasilsmp.mods.jtmcraft.fabric.client.screen.EllipseTracerScreenHandler;
import net.fasilsmp.mods.jtmcraft.shapes.EllipseShapeGenerator;
import net.fasilsmp.mods.jtmcraft.shapes.ShapeGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class EllipseTracerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    public static final int SEMI_MAJOR_AXIS_INDEX = 0;
    public static final int SEMI_MINOR_AXIS_INDEX = 1;

    protected int majorAxis;
    protected int minorAxis;
    protected final PropertyDelegate propertyDelegate;
    private final ShapeGenerator ellipseGenerator;

    public EllipseTracerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.ELLIPSE_STONE_BE_TYPE, blockPos, blockState);
        this.ellipseGenerator = new EllipseShapeGenerator(blockPos, 8, 8);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case SEMI_MAJOR_AXIS_INDEX -> EllipseTracerBlockEntity.this.majorAxis;
                    case SEMI_MINOR_AXIS_INDEX -> EllipseTracerBlockEntity.this.minorAxis;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case SEMI_MAJOR_AXIS_INDEX -> EllipseTracerBlockEntity.this.majorAxis = value;
                    case SEMI_MINOR_AXIS_INDEX -> EllipseTracerBlockEntity.this.minorAxis = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("major_axis", majorAxis);
        nbt.putInt("minor_axis", minorAxis);
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        majorAxis = nbt.getInt("major_axis");
        minorAxis = nbt.getInt("minor_axis");
        super.readNbt(nbt);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, @NotNull PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Ellipse Tracer");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new EllipseTracerScreenHandler(syncId, this, propertyDelegate);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public void tick(ServerWorld serverWorld, BlockPos blockPos, BlockState blockState) {
    }

    public void setMajorAxis(int majorAxis) {
        propertyDelegate.set(SEMI_MAJOR_AXIS_INDEX, majorAxis);
        markDirty();
    }

    public int getMajorAxis() {
        return propertyDelegate.get(SEMI_MAJOR_AXIS_INDEX);
    }

    public int getMinorAxis() {
        return propertyDelegate.get(SEMI_MINOR_AXIS_INDEX);
    }

    public Set<BlockPos> getRelativePlotPointsToRender() {
        ellipseGenerator.generatePlotPoints();
        return ellipseGenerator.getRelativePlotPoints();
    }
}

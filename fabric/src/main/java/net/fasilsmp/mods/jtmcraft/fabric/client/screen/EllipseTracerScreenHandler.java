package net.fasilsmp.mods.jtmcraft.fabric.client.screen;

import net.fasilsmp.mods.jtmcraft.fabric.blockentity.EllipseTracerBlockEntity;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ScreenHandlersRegistration;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.NotNull;

public class EllipseTracerScreenHandler extends ScreenHandler {
    private final EllipseTracerBlockEntity ellipseStoneBlockEntity;
    private final PropertyDelegate propertyDelegate;

    public EllipseTracerScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, @NotNull PacketByteBuf packetByteBuf) {
        this(syncId, playerInventory.player.getWorld().getBlockEntity(packetByteBuf.readBlockPos()), new ArrayPropertyDelegate(2));
    }

    public EllipseTracerScreenHandler(int syncId, BlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ScreenHandlersRegistration.ELLIPSE_BLOCK_SCREEN_HANDLER, syncId);
        this.ellipseStoneBlockEntity = (EllipseTracerBlockEntity) blockEntity;
        this.propertyDelegate = propertyDelegate;
        addProperties(this.propertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public void setMajorAxis(int majorAxis) {
        ellipseStoneBlockEntity.setMajorAxis(majorAxis);
    }

    public int getMajorAxis() {
        return propertyDelegate.get(EllipseTracerBlockEntity.SEMI_MAJOR_AXIS_INDEX);
    }

    public void setMinorAxis(int minorAxis) {
        propertyDelegate.set(EllipseTracerBlockEntity.SEMI_MINOR_AXIS_INDEX, minorAxis);
    }

    public int getMinorAxis() {
        return propertyDelegate.get(EllipseTracerBlockEntity.SEMI_MINOR_AXIS_INDEX);
    }
}

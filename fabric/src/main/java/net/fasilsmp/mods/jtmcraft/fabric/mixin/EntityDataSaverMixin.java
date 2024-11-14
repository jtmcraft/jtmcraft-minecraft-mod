package net.fasilsmp.mods.jtmcraft.fabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityDataSaverMixin implements EntityDataSaver {
    @Unique
    private static final String KEY = "jtmcraft.entity.custom_data";

    @Unique
    private NbtCompound persistentData;

    @Override
    public NbtCompound jtmcraft$getPersistentData() {
        if (persistentData == null) {
            persistentData = new NbtCompound();
        }

        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectedWriteMethod(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> callbackInfoReturnable) {
        if (persistentData != null) {
            nbt.put(KEY, persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectedReadMethod(@NotNull NbtCompound nbt, CallbackInfo callbackInfo) {
        if (nbt.contains("jtmcraft.custom_data", NbtElement.COMPOUND_TYPE)) {
            persistentData = nbt.getCompound(KEY);
        }
    }
}

package net.fasilsmp.mods.jtmcraft.fabric.blockentity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.fasilsmp.mods.jtmcraft.fabric.block.MotionDetectorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class MotionDetectorBlockEntity extends BlockEntity implements GameEventListener.Holder<Vibrations.VibrationListener>, Vibrations {
    private static final Logger LOGGER = LogUtils.getLogger();
    private Vibrations.ListenerData listenerData;
    private final Vibrations.VibrationListener listener;
    private final Vibrations.Callback callback;
    private int lastVibrationFrequency;

    public MotionDetectorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.MOTION_DETECTOR_BE_TYPE, blockPos, blockState);
        this.callback = this.createCallback();
        this.listenerData = new Vibrations.ListenerData();
        this.listener = new Vibrations.VibrationListener(this);
    }

    public Vibrations.Callback createCallback() {
        return new MotionDetectorBlockEntity.VibrationCallback(this.getPos());
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
        if (nbt.contains("listener", 10)) {
            ListenerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("listener")))
                    .resultOrPartial(LOGGER::error)
                    .ifPresent((data) -> this.listenerData = data);
        }
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
        ListenerData.CODEC.encodeStart(NbtOps.INSTANCE, this.listenerData)
                .resultOrPartial(LOGGER::error)
                .ifPresent((listener) -> nbt.put("listener", listener));
    }

    public Vibrations.ListenerData getVibrationListenerData() {
        return this.listenerData;
    }

    public Vibrations.Callback getVibrationCallback() {
        return this.callback;
    }

    public void setLastVibrationFrequency(int lastVibrationFrequency) {
        this.lastVibrationFrequency = lastVibrationFrequency;
    }

    public Vibrations.VibrationListener getEventListener() {
        return this.listener;
    }

    protected class VibrationCallback implements Vibrations.Callback {
        protected final BlockPos blockPos;
        private final PositionSource positionSource;

        public VibrationCallback(BlockPos blockPos) {
            this.blockPos = blockPos;
            this.positionSource = new BlockPositionSource(blockPos);
        }

        public int getRange() {
            return 8;
        }

        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        public boolean triggersAvoidCriterion() {
            return true;
        }

        public boolean accepts(ServerWorld world, BlockPos blockPos, GameEvent event, @Nullable GameEvent.Emitter emitter) {
            return !blockPos.equals(this.blockPos);
        }

        public void accept(ServerWorld serverWorld, BlockPos blockPos, GameEvent gameEvent, @Nullable Entity sourceEntity, @Nullable Entity entity, float distance) {
            BlockState blockstate = MotionDetectorBlockEntity.this.getCachedState();
            if (MotionDetectorBlock.canActivate(blockstate)) {
                MotionDetectorBlockEntity.this.setLastVibrationFrequency(Vibrations.getFrequency(gameEvent));
                Block block = blockstate.getBlock();
                if (block instanceof MotionDetectorBlock motionDetectorBlock) {
                    motionDetectorBlock.activate(serverWorld, this.blockPos, blockstate);
                }
            }
        }

        public void onListen() {
            MotionDetectorBlockEntity.this.markDirty();
        }

        public boolean requiresTickingChunksAround() {
            return true;
        }
    }
}

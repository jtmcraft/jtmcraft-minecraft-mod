package net.fasilsmp.mods.jtmcraft.fabric.client.render;

import net.fasilsmp.mods.jtmcraft.fabric.blockentity.EllipseTracerBlockEntity;
import net.fasilsmp.mods.jtmcraft.util.ColoredGlassChanger;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.List;
import java.util.Set;

public class EllipseTracerEntityRenderer implements BlockEntityRenderer<EllipseTracerBlockEntity> {
    private final BlockEntityRendererFactory.Context context;
    private final ColoredGlassChanger coloredGlassChanger;

    public EllipseTracerEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.context = context;
        this.coloredGlassChanger = new ColoredGlassChanger(List.of("white", "light_gray", "gray", "black"), 1);
    }

    @Override
    public void render(@NotNull EllipseTracerBlockEntity ellipseTracerBlockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = ellipseTracerBlockEntity.getWorld();
        if (world == null || ellipseTracerBlockEntity.getMajorAxis() < 2 || ellipseTracerBlockEntity.getMinorAxis() < 2) {
            return;
        }

        Set<BlockPos> plotPoints = ellipseTracerBlockEntity.getRelativePlotPointsToRender();
        plotPoints.forEach(blockPos -> {
            matrices.push();
            translateToRenderPosition(matrices, blockPos);
            applyTransformation(tickDelta, matrices, world);
            renderGhostBlock(ellipseTracerBlockEntity, matrices, vertexConsumers, world, tickDelta);
            matrices.pop();
        });
    }

    private void translateToRenderPosition(@NotNull MatrixStack matrices, @NotNull BlockPos blockPos) {
        matrices.translate(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    private void applyTransformation(float tickDelta, @NotNull MatrixStack matrices, @NotNull World world) {
        float angle = (world.getTime() + tickDelta) * 4;
        applyRotation(matrices, angle);
        applyScaling(matrices, angle);
    }

    private void applyRotation(@NotNull MatrixStack matrices, float angle) {
        Quaternionf rotation = RotationAxis.POSITIVE_Y.rotationDegrees(angle);
        matrices.translate(0.5F, 0F, 0.5F);
        matrices.multiply(rotation);
    }

    private void applyScaling(@NotNull MatrixStack matrices, float angle) {
        double radiansAngle = Math.toRadians(angle);
        float scaleFraction = (float)((1 + Math.sin(radiansAngle)) / 2);
        float scale = 0.9F + scaleFraction;
        matrices.scale(scale, scale, scale);
    }

    private void renderGhostBlock(@NotNull EllipseTracerBlockEntity ellipseTracerBlockEntity, @NotNull MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, float tickDelta) {
        int lightMapCoordsAboveRenderedEntity = WorldRenderer.getLightmapCoordinates(world, ellipseTracerBlockEntity.getPos().up());
        ItemStack glass = new ItemStack(Registries.BLOCK.get(new Identifier("minecraft", coloredGlassChanger.selectColoredGlass(tickDelta, world))));
        context.getItemRenderer().renderItem(glass, ModelTransformationMode.GROUND, lightMapCoordsAboveRenderedEntity, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, world, 0);
    }
}

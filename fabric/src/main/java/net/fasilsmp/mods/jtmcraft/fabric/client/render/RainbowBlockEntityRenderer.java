package net.fasilsmp.mods.jtmcraft.fabric.client.render;

import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.RainbowBlockEntity;
import net.fasilsmp.mods.jtmcraft.util.ColorChanger;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RainbowBlockEntityRenderer implements BlockEntityRenderer<RainbowBlockEntity> {
    public static final Identifier BEAM_TEXTURE = Jtmcraft.id("textures/entity/rainbow_block_beam.png");
    public static final int MAX_BEAM_HEIGHT = 2;
    private final BlockEntityRendererFactory.Context context;
    private final ColorChanger colorChanger;

    public RainbowBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.context = context;
        this.colorChanger = new ColorChanger();
    }

    @Override
    public void render(@NotNull RainbowBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        if (world == null) {
            return;
        }

        long worldTime = world.getTime();
        int yOffset = 0;
        for (int m = 0; m < MAX_BEAM_HEIGHT; ++m) {
            colorChanger.updateHue();
            float[] normalizedColor = colorChanger.generateNormalizedColorArray();
            constructAndRenderBeam(matrices, vertexConsumers, light, worldTime, yOffset, m == MAX_BEAM_HEIGHT - 1 ? MAX_BEAM_HEIGHT : new BeaconBlockEntity.BeamSegment(normalizedColor).getHeight(), normalizedColor);
            yOffset += new BeaconBlockEntity.BeamSegment(normalizedColor).getHeight();
        }

        ItemRenderer itemRenderer = context.getItemRenderer();
        ItemStack itemStack = entity.getRenderStack();
        matrices.push();
        matrices.translate(1.5F, 1.75F, 1.5F);
        matrices.scale(0.5F, 0.5F, 0.5F);
        int lightLevel = getLightLevel(world, entity.getPos());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((world.getTime() + tickDelta) * 4));
        itemRenderer.renderItem(itemStack, ModelTransformationMode.GUI, lightLevel, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, world, 1);
        matrices.pop();

        RenderLayers.getItemLayer(itemStack, false);
    }

    private int getLightLevel(@NotNull World world, @NotNull BlockPos pos) {
        return WorldRenderer.getLightmapCoordinates(world, pos.up());
    }

    private void constructAndRenderBeam(MatrixStack matrices, VertexConsumerProvider vertexConsumers, float light, long worldTime, int yOffset, int height, float[] color) {
        BeaconBlockEntity.BeamSegment beamSegment = new BeaconBlockEntity.BeamSegment(color);
        renderBeam(matrices, vertexConsumers, light, worldTime, yOffset, height, beamSegment.getColor());
    }

    private static void renderBeam(MatrixStack matrices, VertexConsumerProvider vertexConsumers, float tickDelta, long worldTime, int yOffset, int maxY, float[] color) {
        renderBeam(matrices, vertexConsumers, BEAM_TEXTURE, tickDelta, 1.0F, worldTime, yOffset, maxY, color, 0.2F, 0.25F);
    }

    private static void renderBeam(@NotNull MatrixStack matrices, @NotNull VertexConsumerProvider vertexConsumers, Identifier textureId,
                                   float tickDelta, float heightScale, long worldTime, int yOffset, int maxY, float @NotNull [] color,
                                   float innerRadius, float outerRadius) {
        int i = yOffset + maxY;
        matrices.push();
        matrices.translate(0.5, 0.0, 0.5);
        float worldTimeMod = (float)Math.floorMod(worldTime, 40) + tickDelta;
        float timeDirection = maxY < 0 ? worldTimeMod : -worldTimeMod;
        float fractionalPart = MathHelper.fractionalPart(timeDirection * 0.2F - (float)MathHelper.floor(timeDirection * 0.1F));
        float red = color[0];
        float green = color[1];
        float blue = color[2];
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(worldTimeMod * 2.25F - 45.0F));

        float negativeInnerRadius = -innerRadius;
        float heightOffset = -1.0F + fractionalPart;
        float heightScaledMax = (float)maxY * heightScale * (0.5F / innerRadius) + heightOffset;
        renderBeamLayer(matrices, vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(textureId, false)),
                red, green, blue, 1.0F, yOffset, i, 0.0F, innerRadius, innerRadius,
                0.0F, negativeInnerRadius, 0.0F, 0.0F, negativeInnerRadius,
                0.0F, 1.0F, heightScaledMax, heightOffset);
        matrices.pop();

        OuterBeamParam outerBeamParam = calculateOuterBeamParam(heightScale, worldTime, outerRadius, maxY);

        renderBeamLayer(matrices, vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(textureId, true)),
                red, green, blue, 0.125F, yOffset, i, outerBeamParam.m, outerBeamParam.n,
                outerRadius, outerBeamParam.p, outerBeamParam.q, outerRadius, outerRadius,
                outerRadius, 0.0F, 1.0F, outerBeamParam.x, outerBeamParam.w);
        matrices.pop();
    }

    private static @NotNull OuterBeamParam calculateOuterBeamParam(float heightScale, long worldTime, float outerRadius, int maxY) {
        float m = -outerRadius;
        float n = -outerRadius;
        float p = -outerRadius;
        float q = -outerRadius;
        float w = -1.0F + MathHelper.fractionalPart(((float)Math.floorMod(worldTime, 40) + worldTime) * 0.2F - (float)MathHelper.floor(((float)Math.floorMod(worldTime, 40) + worldTime) * 0.1F));
        float x = (float)maxY * heightScale + w;
        return new OuterBeamParam(m, n, p, q, w, x);
    }

    private static class OuterBeamParam {
        float m;
        float n;
        float p;
        float q;
        float w;
        float x;

        OuterBeamParam(float m, float n, float p, float q, float w, float x) {
            this.m = m;
            this.n = n;
            this.p = p;
            this.q = q;
            this.w = w;
            this.x = x;
        }
    }

    private static void renderBeamLayer(@NotNull MatrixStack matrices, VertexConsumer vertices, float red, float green, float blue, float alpha, int yOffset, int height, float x1, float z1, float x2, float z2, float x3, float z3, float x4, float z4, float u1, float u2, float v1, float v2) {
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x1, z1, x2, z2, u1, u2, v1, v2);
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x4, z4, x3, z3, u1, u2, v1, v2);
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x2, z2, x4, z4, u1, u2, v1, v2);
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x3, z3, x1, z1, u1, u2, v1, v2);
    }

    private static void renderBeamFace(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertices, float red, float green, float blue, float alpha, int yOffset, int height, float x1, float z1, float x2, float z2, float u1, float u2, float v1, float v2) {
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, height, x1, z1, u2, v1);
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, yOffset, x1, z1, u2, v2);
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, yOffset, x2, z2, u1, v2);
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, height, x2, z2, u1, v1);
    }

    private static void renderBeamVertex(Matrix4f positionMatrix, Matrix3f normalMatrix, @NotNull VertexConsumer vertices, float red, float green, float blue, float alpha, int y, float x, float z, float u, float v) {
        vertices.vertex(positionMatrix, x, (float)y, z).color(red, green, blue, alpha).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }
}

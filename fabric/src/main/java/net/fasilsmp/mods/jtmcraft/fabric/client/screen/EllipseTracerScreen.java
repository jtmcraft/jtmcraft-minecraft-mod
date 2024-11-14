package net.fasilsmp.mods.jtmcraft.fabric.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class EllipseTracerScreen extends HandledScreen<EllipseTracerScreenHandler> {
    private static final Identifier BG_TEXTURE = Jtmcraft.id("textures/gui/ellipse_stone_gui.png");
    private static final int SCALE_MIN = 1;
    private static final int SCALE_MAX = 60;
    private int backgroundX;
    private int backgroundY;

    private int majorAxis;
    private int minorAxis;

    public EllipseTracerScreen(EllipseTracerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        titleY = 1000;
        playerInventoryTitleY = 1000;
        backgroundX = (width - backgroundWidth) / 2;
        backgroundY = (height - backgroundHeight) / 2;
        backgroundWidth = 160;
        backgroundHeight = 80;

        MajorAxisChangeHandler majorAxisChangeHandler = new MajorAxisChangeHandler();
        MinorAxisChangeHandler minorAxisChangeHandler = new MinorAxisChangeHandler();

        assignInitialAxisValues();

        createAndAddAxisSliderWidget(width / 2 - 120 + backgroundX,
                10 + backgroundY, "Major Axis", majorAxis, majorAxisChangeHandler);
        createAndAddAxisSliderWidget(width / 2 - 120 + backgroundX,
                40 + backgroundY, "Minor axis", minorAxis, minorAxisChangeHandler);
    }

    private void assignInitialAxisValues() {
        majorAxis = handler.getMajorAxis();
        minorAxis = handler.getMinorAxis();
    }

    private void createAndAddAxisSliderWidget(int x, int y, String text, int defaultValue, @NotNull Consumer<Double> onAxisValueChange){
        AxisSliderWidget sliderWidget = new AxisSliderWidget(x, y, Text.literal(text), defaultValue, onAxisValueChange);
        addDrawableChild(sliderWidget);
    }

    @Override
    protected void drawBackground(@NotNull DrawContext context, float delta, int mouseX, int mouseY) {
        renderGuiBackground(context);
    }

    private void renderGuiBackground(@NotNull DrawContext context) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        context.drawTexture(BG_TEXTURE, backgroundX, backgroundY, 0, 0, backgroundWidth, backgroundHeight);
    }

    private class MajorAxisChangeHandler implements Consumer<Double> {
        @Override
        public void accept(@NotNull Double axisValue) {
            majorAxis = axisValue.intValue();
            handler.setMajorAxis(majorAxis);
        }
    }

    private class MinorAxisChangeHandler implements Consumer<Double> {
        @Override
        public void accept(@NotNull Double axisValue) {
            minorAxis = axisValue.intValue();
            handler.setMinorAxis(minorAxis);
        }
    }

    private static class AxisSliderWidget extends SliderWidget {
        private static final int SCALE_MIN = 1;
        private static final int SCALE_MAX = 60;
        private final Consumer<Double> onValueChange;

        public AxisSliderWidget(int x, int y, Text text, double value, Consumer<Double> onValueChange) {
            super(x, y, 120, 20, text, value);
            this.onValueChange = onValueChange;
        }

        @Override
        protected void updateMessage() {
            // TODO update according to mouse position on the slider
            setTooltip(Tooltip.of(Text.literal(String.valueOf(scaledToRange().intValue()))));
        }

        @Override
        protected void applyValue() {
            onValueChange.accept(scaledToRange());
        }

        private Double scaledToRange() {
            return (double) (SCALE_MIN + Math.round(value * (SCALE_MAX - SCALE_MIN)));
        }
    }
}

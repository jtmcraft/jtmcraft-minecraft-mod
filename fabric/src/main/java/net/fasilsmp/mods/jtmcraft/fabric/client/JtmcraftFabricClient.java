package net.fasilsmp.mods.jtmcraft.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.blockentity.BlockEntities;
import net.fasilsmp.mods.jtmcraft.fabric.client.render.EllipseTracerEntityRenderer;
import net.fasilsmp.mods.jtmcraft.fabric.client.render.RainbowBlockEntityRenderer;
import net.fasilsmp.mods.jtmcraft.fabric.client.screen.EllipseTracerScreen;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ScreenHandlersRegistration;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public final class JtmcraftFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Jtmcraft.LOGGER.info("Client initializing");

        HandledScreens.register(ScreenHandlersRegistration.ELLIPSE_BLOCK_SCREEN_HANDLER, EllipseTracerScreen::new);
        BlockEntityRendererFactories.register(BlockEntities.RAINBOW_BLOCK_BE_TYPE, RainbowBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.ELLIPSE_STONE_BE_TYPE, EllipseTracerEntityRenderer::new);
    }
}

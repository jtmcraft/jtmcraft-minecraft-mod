package net.fasilsmp.mods.jtmcraft.fabric.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemsRegistration;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class JtmcraftCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> commandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(
                CommandManager.literal("jtmcraft")
                        .then(CommandManager.literal("ode_to_joy").executes(JtmcraftCommand::odeToJoyCommand))
                        .then(CommandManager.literal("sunset").executes(JtmcraftCommand::sunsetCommand))
                        .then(CommandManager.literal("glad_youre_here").executes(JtmcraftCommand::gladYoureHereCommand))
                        .then(CommandManager.literal("block_party").executes(JtmcraftCommand::blockPartyCommand))
                        .then(CommandManager.literal("run_and_jump").executes(JtmcraftCommand::runAndJumpCommand))
        );

        Jtmcraft.LOGGER.info("Registered /jtmcraft command");
    }

    public static int processCommand(@NotNull CommandContext<ServerCommandSource> commandContext, Item music_disc) {
        ServerPlayerEntity serverPlayerEntity = commandContext.getSource().getPlayer();

        if (serverPlayerEntity == null) {
            return 0;
        }

        BlockPos playerBlockPos = serverPlayerEntity.getBlockPos();
        ItemStack musicItemStack = new ItemStack(music_disc, 1);
        return giveMusicDisc(serverPlayerEntity, playerBlockPos, musicItemStack);
    }

    public static int gladYoureHereCommand(@NotNull CommandContext<ServerCommandSource> commandContext) {
        return processCommand(commandContext, ItemsRegistration.MUSIC_DISC_GLAD_YOURE_HERE);
    }

    public static int blockPartyCommand(@NotNull CommandContext<ServerCommandSource> commandContext) {
        return processCommand(commandContext, ItemsRegistration.MUSIC_DISC_BLOCK_PARTY);
    }

    public static int runAndJumpCommand(@NotNull CommandContext<ServerCommandSource> commandContext) {
        return processCommand(commandContext, ItemsRegistration.MUSIC_DISC_RUN_AND_JUMP);
    }

    public static int sunsetCommand(@NotNull CommandContext<ServerCommandSource> commandContext) {
        return processCommand(commandContext, ItemsRegistration.MUSIC_DISC_SUNSET);
    }

    public static int odeToJoyCommand(@NotNull CommandContext<ServerCommandSource> commandContext) {
        return processCommand(commandContext, ItemsRegistration.MUSIC_DISC_ODE_TO_JOY);
    }

    private static int giveMusicDisc(@NotNull ServerPlayerEntity serverPlayerEntity, BlockPos playerBlockPos, ItemStack odeToJoy) {
        boolean success = serverPlayerEntity.getInventory().insertStack(odeToJoy);
        ItemEntity discEntity = serverPlayerEntity.dropItem(odeToJoy, false);

        if (success) {
            if (discEntity != null) {
                discEntity.setDespawnImmediately();
            }

            float volume = 0.2F;
            float pitch = ((serverPlayerEntity.getRandom().nextFloat() - serverPlayerEntity.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F;

            serverPlayerEntity.getWorld().playSound(null, playerBlockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, volume, pitch);
            serverPlayerEntity.currentScreenHandler.sendContentUpdates();
        } else {
            if (discEntity != null) {
                discEntity.resetPickupDelay();
                discEntity.setOwner(serverPlayerEntity.getUuid());
            }
        }

        return 1;
    }
}

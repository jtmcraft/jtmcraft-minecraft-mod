package net.fasilsmp.mods.jtmcraft.fabric.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemsRegistration;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.ItemEntity;
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
        Jtmcraft.LOGGER.info("Registering jtmcraft ode_to_joy command");

        commandDispatcher.register(CommandManager.literal("jtmcraft")
                .then(CommandManager.literal("ode_to_joy").executes(JtmcraftCommand::odeToJoyCommand)));
    }

    public static int odeToJoyCommand(@NotNull CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
        ServerPlayerEntity serverPlayerEntity = commandContext.getSource().getPlayer();

        if (serverPlayerEntity == null) {
            return 0;
        }

        BlockPos playerBlockPos = serverPlayerEntity.getBlockPos();
        ItemStack odeToJoy = new ItemStack(ItemsRegistration.MUSIC_DISC_ODE_TO_JOY, 1);
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

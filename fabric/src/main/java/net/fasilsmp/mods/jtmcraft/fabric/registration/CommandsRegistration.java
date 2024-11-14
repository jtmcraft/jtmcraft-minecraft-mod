package net.fasilsmp.mods.jtmcraft.fabric.registration;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.fasilsmp.mods.jtmcraft.fabric.command.JtmcraftCommand;

public class CommandsRegistration {
    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft commands");

        CommandRegistrationCallback.EVENT.register(JtmcraftCommand::register);
    }
}
